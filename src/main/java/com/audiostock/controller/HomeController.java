package com.audiostock.controller;

import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
