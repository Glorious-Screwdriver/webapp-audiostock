package com.audiostock.controller.consumer;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {

    TrackService trackService;
    UserService userService;

    public FavoriteController(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    @GetMapping
    public String favorite(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        // Printing username in the header
        model.addAttribute("user", user);

        // Track map
        List<Track> favorites = userService.getFavoriteSortedByName(user);
        Map<Track, Boolean[]> map = Utils.getTrackMap(user, favorites, trackService);
        model.addAttribute("tracks", map);

        return "favorite";
    }

}
