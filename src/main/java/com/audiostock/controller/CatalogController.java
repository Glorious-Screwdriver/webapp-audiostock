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

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class CatalogController {

    private final UserService userService;
    private final StatusService statusService;
    private final TrackService trackService;

    public CatalogController(UserService userService, StatusService statusService, TrackService trackService) {
        this.userService = userService;
        this.statusService = statusService;
        this.trackService = trackService;
    }

    @GetMapping("/")
    public String index(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        if (user != null && statusService.isModerator(user)) {
            //TODO moderation view
            throw new UnsupportedOperationException("/moderation view is not supported");
        }

        // Printing username in the header
        model.addAttribute("logged", user != null);
        if (user != null) {
            model.addAttribute("user", user);
        }

        // Track map
        final List<Track> tracks = trackService.getAllActive();
        Map<Track, Boolean[]> map = Utils.getTrackMap(user, tracks, trackService);
        model.addAttribute("tracks", map);

        return "index";
    }

}
