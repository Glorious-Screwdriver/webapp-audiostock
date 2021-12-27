package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UserNotFoundException;
import org.springframework.stereotype.Controller;
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
    public String favorite(Principal principal) {
        User user = getUserFromPrincipal(principal);
        List<Track> favorites = userService.getFavoriteSortedByName(user);

        //TODO /favorite view
        throw new UnsupportedOperationException();
    }

    private User getUserFromPrincipal(Principal principal) {
        User user;
        try {
            user = userService.getUserByUsername(principal.getName());
        } catch (UserNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return user;
    }

}
