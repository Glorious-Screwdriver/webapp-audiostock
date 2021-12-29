package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
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

    TrackService trackService;
    UserService userService;

    public CatalogController(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    @GetMapping("/")
    public String index(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        // Printing username in the header
        model.addAttribute("logged", user != null);
        if (user != null) {
            model.addAttribute("username", user.getLogin());
        }

        // Track map
        final List<Track> tracks = trackService.getAll();
        Map<Track, Boolean[]> map = Utils.getTrackMap(user, tracks, trackService);
        model.addAttribute("tracks", map);

        return "index";
    }

}
