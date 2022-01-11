package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.repos.StatusRepo;
import com.audiostock.service.StatusService;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CatalogController {

    UserService userService;
    TrackService trackService;
    StatusService statusService;

    public CatalogController(UserService userService, TrackService trackService, StatusService statusService) {
        this.userService = userService;
        this.trackService = trackService;
        this.statusService = statusService;
    }

    @GetMapping("/")
    public String index(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        if (user != null && user.getStatus() == statusService.getModerator()) {
            //TODO moderation view
            throw new UnsupportedOperationException("/moderation view is not supported");
        }

        // Printing username in the header
        model.addAttribute("logged", user != null);
        if (user != null) {
            model.addAttribute("user", user);
        }

        // Track map
        final List<Track> tracks = trackService.getAll();
        Map<Track, Boolean[]> map = Utils.getTrackMap(user, tracks, trackService);
        model.addAttribute("tracks", map);

        return "index";
    }

}
