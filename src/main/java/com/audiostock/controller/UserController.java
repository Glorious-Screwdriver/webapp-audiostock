package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UserNotFoundException;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;
    TrackService trackService;

    public UserController(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    // Representation

    @GetMapping("/{username}")
    public String getAuthor(@PathVariable String username, Model model, Principal principal)
            throws UserNotFoundException {
        // Author info
        User author = userService.getUserByUsername(username);
        model.addAttribute("author", author);

        // Printing username in the header
        User user = Utils.getUserFromPrincipal(principal, userService);
        model.addAttribute("logged", user != null);
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "user";
    }

    @GetMapping("/{username}/tracks")
    public String getAuthorTracks(@PathVariable String username, Model model, Principal principal) throws UserNotFoundException {
        // Author info
        User author = userService.getUserByUsername(username);
        model.addAttribute("author", author);

        // Printing username in the header
        User user = Utils.getUserFromPrincipal(principal, userService);
        model.addAttribute("logged", user != null);
        if (user != null) {
            model.addAttribute("user", user);
        }

        // Track map
        List<Track> releases = userService.getActiveReleasesSortedByName(author);
        Map<Track, Boolean[]> map = Utils.getTrackMap(user, releases, trackService);
        model.addAttribute("tracks", map);

        return "user";
    }

    // ExceptionHandlers

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(UserNotFoundException e) {
        e.printStackTrace();

        //TODO userNotFound view
        throw new UnsupportedOperationException(e);
    }

}
