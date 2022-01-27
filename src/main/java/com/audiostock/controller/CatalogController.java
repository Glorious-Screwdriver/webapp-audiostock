package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.StatusService;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class CatalogController {

    private final UserService userService;
    private final StatusService statusService;
    private final TrackService trackService;

    public CatalogController(UserService userService, StatusService statusService, TrackService trackService) {
        this.userService = userService;
        this.statusService = statusService;
        this.trackService = trackService;
    }

    @GetMapping
    public String index(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);
        if (user != null && statusService.isModerator(user)) return "redirect:/moderation";
        extractUserInfo(user, model);

        extractTrackMap(model, user, trackService.getAllActive());

        return "index";
    }

    @GetMapping(params = {"name", "genre", "mood"})
    public String search(Principal principal, Model model,
                       @RequestParam String name,
                       @RequestParam String genre,
                       @RequestParam String mood) {
        User user = Utils.getUserFromPrincipal(principal, userService);
        if (user != null && statusService.isModerator(user)) return "redirect:/moderation";
        extractUserInfo(user, model);

        extractTrackMap(model, user, trackService.search(name, genre, mood));

        return "index";
    }

    private void extractUserInfo(User user, Model model) {
        model.addAttribute("logged", user != null);
        if (user != null) {
            model.addAttribute("user", user);
        }
    }

    private void extractTrackMap(Model model, User user, List<Track> tracks) {
        Map<Track, Boolean[]> map = Utils.getTrackMap(user, tracks, trackService);
        model.addAttribute("tracks", map);
    }

}
