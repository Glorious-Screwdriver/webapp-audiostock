package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.TrackNotFoundException;
import com.audiostock.service.exceptions.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
public class HomeController {

    TrackService trackService;
    UserService userService;

    public HomeController(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    @GetMapping("/")
    public String index(Principal principal, Model model) {
        model.addAttribute("logged", principal != null);
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        model.addAttribute("tracks", trackService.getAll());

        return "index";
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String ugh(Principal principal, Model model, @RequestBody MultiValueMap<String, String> paramMap) {
        //TODO block post requests from guests to here
        System.err.println("POST /");

        if (paramMap.containsKey("add-to-fav")) {
            try {
                // DEBUG
                System.out.println("Add-to-fav");

                User user = userService.getUserByUsername(principal.getName());
                Track track = trackService.getTrackById(Long.parseLong(paramMap.get("add-to-fav").get(0)));

                // DEBUG
                System.out.println(user.getLogin());
                System.out.println(track.getName());

                userService.addTrackToFavorite(user, track);
            } catch (UserNotFoundException | TrackNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (paramMap.containsKey("add-to-cart")) {
            try {
                // DEBUG
                System.out.println("Add-to-cart");

                User user = userService.getUserByUsername(principal.getName());
                Track track = trackService.getTrackById(Long.parseLong(paramMap.get("add-to-cart").get(0)));

                // DEBUG
                System.out.println(user.getLogin());
                System.out.println(track.getName());

                userService.addTrackToCart(user, track);
            } catch (UserNotFoundException | TrackNotFoundException e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("username", principal.getName());
        model.addAttribute("logged", true);
        model.addAttribute("tracks", trackService.getAll());

        return "index";
    }

}
