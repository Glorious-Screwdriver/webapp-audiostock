package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UserNotFoundException;
import com.audiostock.service.exceptions.UserNotLoggedInException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String getUser(@PathVariable String username) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);

        //TODO users/{username} view
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{username}/tracks")
    public String getAuthorTracks(@PathVariable String username) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);
        List<Track> releases = userService.getReleasesSortedByName(user);

        //TODO /{username}/tracks view
        throw new UnsupportedOperationException();
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
