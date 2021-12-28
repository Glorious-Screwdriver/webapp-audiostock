package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UserNotFoundException;
import com.audiostock.service.exceptions.UserNotLoggedInException;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Representation

    @GetMapping("/{username}")
    public String getUser(@PathVariable String username, Model model, Principal principal) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);
        model.addAttribute("author", user);
        model.addAttribute("logged", principal != null);
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "user";
    }

    @GetMapping("/{username}/tracks")
    public String getAuthorTracks(@PathVariable String username, Model model, Principal principal) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);
        List<Track> releases = userService.getReleasesSortedByName(user);
        model.addAttribute("author", user);
        model.addAttribute("logged", principal != null);
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("tracks",releases);
        return "user";
    }

    // ExceptionHandlers

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(UserNotFoundException e) {
        e.printStackTrace();

        //TODO userNotFound view
        throw new UnsupportedOperationException(e);
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public String userNotLoggedIn(UserNotLoggedInException e) {
        e.printStackTrace();

        //TODO userNotLoggedIn view
        throw new UnsupportedOperationException(e);
    }

}
