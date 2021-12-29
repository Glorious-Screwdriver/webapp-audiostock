package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.TrackIsNotActiveException;
import com.audiostock.service.exceptions.TrackNotFoundException;
import com.audiostock.service.exceptions.UserNotLoggedInException;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/track")
public class TrackController {

    UserService userService;
    TrackService trackService;

    public TrackController(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    // Representation

    @GetMapping("/{trackId}")
    public String getTrack(@PathVariable Long trackId, Model model, Principal principal)
            throws TrackNotFoundException, TrackIsNotActiveException {
        Track track = trackService.getTrackById(trackId);

        if (principal == null) {
            if (!track.isActive()) throw new TrackIsNotActiveException(String.valueOf(trackId));
        } else {
            User user = Utils.getUserFromPrincipalNoException(principal, userService);
            if (!track.isActive() && !user.getStatus().getName().equals("MODERATOR")) {
                throw new TrackIsNotActiveException(String.valueOf(trackId));
            }
        }

        model.addAttribute("logged", principal != null);
        if (principal != null) {
            model.addAttribute("username", principal.getName());
            model.addAttribute("carted",trackService.isInCart(track,user));
            model.addAttribute("stared",trackService.isInFavorite(track,user));
        }
        model.addAttribute("track",track);
        return "track";
    }

    // Buttons

    @PostMapping("/{trackId}/addToFavorite")
    public String addTrackToFavorite(@PathVariable Long trackId, Principal principal, @RequestHeader String referer)
            throws TrackNotFoundException, UserNotLoggedInException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);
        System.out.println(track.getName() + " to favorite-->" + user.getLogin());

        boolean added = userService.addTrackToFavorite(user, track);
        System.err.println(added);
        return "redirect:" + referer;
    }

    @PostMapping("/{trackId}/removeFromFavorite")
    public String removeTrackFromFavorite(@PathVariable Long trackId, Principal principal, @RequestHeader String referer)
            throws TrackNotFoundException, UserNotLoggedInException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        boolean removed = userService.removeTrackFromFavorites(user, track);
        return "redirect:" + referer;
    }

    @PostMapping("/{trackId}/addToCart")
    public String addTrackToCart(@PathVariable Long trackId, Principal principal, @RequestHeader String referer)
            throws TrackNotFoundException, UserNotLoggedInException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        boolean added = userService.addTrackToCart(user, track);
        return "redirect:" + referer;
    }

    @PostMapping("/{trackId}/removeFromCart")
    public String removeTrackFromCart(@PathVariable Long trackId, Principal principal, @RequestHeader String referer)
            throws TrackNotFoundException, UserNotLoggedInException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        boolean removed = userService.removeTrackFromCart(user, track);
        return "redirect:" + referer;
    }

    // Exceptions

    @ExceptionHandler(TrackNotFoundException.class)
    public String trackNotFound(TrackNotFoundException e) {
        //TODO trackNotFound view
        throw new UnsupportedOperationException(e);
    }

    @ExceptionHandler(TrackIsNotActiveException.class)
    public String trackIsNotActive(TrackIsNotActiveException e) {
        //TODO trackIsNotActive view
        throw new UnsupportedOperationException(e);
    }

}
