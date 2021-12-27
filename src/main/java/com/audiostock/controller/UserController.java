package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UserNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public String getUser(@PathVariable String username) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);

        //TODO users/{username} view
        throw new UnsupportedOperationException();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound() {
        //TODO userNotFound view
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{username}/tracks")
    public String getAuthorTracks(@PathVariable String username) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);
        List<Track> releases = userService.getReleasesSortedByName(user);

        //TODO /{username}/tracks view
        throw new UnsupportedOperationException();
    }

}
