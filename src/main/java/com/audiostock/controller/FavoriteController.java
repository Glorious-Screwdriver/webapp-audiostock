package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UserNotLoggedInException;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {

    UserService userService;

    public FavoriteController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String favorite(Principal principal, Model model) throws UserNotLoggedInException {
        User user = Utils.getUserFromPrincipal(principal, userService);
        List<Track> favorites = userService.getFavoriteSortedByName(user);
        model.addAttribute("username", principal.getName());
        model.addAttribute("tracks", favorites);
        return "favorite";
    }

}
