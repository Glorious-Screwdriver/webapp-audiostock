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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/purchased")
public class PurchasedController {

    UserService userService;
    TrackService trackService;

    public PurchasedController(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    @GetMapping
    public String purchased(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);
        List<Track> tracks = userService.getPurchasedSortedByName(user);

        model.addAttribute("user", user);

        // Track map
        Map<Track, Boolean[]> map = Utils.getTrackMap(user, tracks, trackService);
        model.addAttribute("tracks", map);

        return "purchased";
    }
}
