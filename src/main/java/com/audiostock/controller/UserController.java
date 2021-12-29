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
import java.util.LinkedHashMap;
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
        User author = userService.getUserByUsername(username);
        model.addAttribute("author", author);

        // Printing username in the header
        User user = Utils.getUserFromPrincipal(principal, userService);

        model.addAttribute("logged", principal != null);
        if (principal != null) {
            model.addAttribute("username", user.getLogin());
        }

        // Track map
        Map<Track, Boolean[]> map = new LinkedHashMap<>();

        List<Track> releases = userService.getReleasesSortedByName(author);
        if (principal != null) {
            for (Track track : releases) {
                map.put(track, new Boolean[]{
                        trackService.isInFavorite(track, user),
                        trackService.isInCart(track, user)
                });
            }
        } else {
            for (Track track : releases) {
                map.put(track, new Boolean[]{false, false});
            }
        }
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
