package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.TrackNotFoundException;
import com.audiostock.service.exceptions.UserNotFoundException;
import org.springframework.stereotype.Controller;
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
    public String getTrack(@PathVariable Long trackId) throws TrackNotFoundException {
        Track track = trackService.getTrackById(trackId);

        //TODO /track/{trackId} view
        throw new UnsupportedOperationException();
    }

    @ExceptionHandler(TrackNotFoundException.class)
    public String trackNotFound() {
        //TODO trackNotFound view
        throw new UnsupportedOperationException();
    }

    // Buttons

    @PostMapping("/{trackId}/addToFavorite")
    public void addTrackToFavorite(@PathVariable Long trackId, Principal principal)
            throws TrackNotFoundException {
        Track track = trackService.getTrackById(trackId);
        User user = getUserFromPrincipal(principal);

        boolean added = userService.addTrackToFavorite(user, track);
    }

    @PostMapping("/{trackId}/removeFromFavorite")
    public void removeTrackFromFavorite(@PathVariable Long trackId, Principal principal)
            throws TrackNotFoundException {
        Track track = trackService.getTrackById(trackId);
        User user = getUserFromPrincipal(principal);

        boolean removed = userService.removeTrackFromFavorites(user, track);
    }

    @PostMapping("/{trackId}/addToCart")
    public void addTrackToCart(@PathVariable Long trackId, Principal principal)
            throws TrackNotFoundException {
        Track track = trackService.getTrackById(trackId);
        User user = getUserFromPrincipal(principal);

        boolean added = userService.addTrackToCart(user, track);
    }

    @PostMapping("/{trackId}/removeFromCart")
    public void removeTrackFromCart(@PathVariable Long trackId, Principal principal)
            throws TrackNotFoundException {
        Track track = trackService.getTrackById(trackId);
        User user = getUserFromPrincipal(principal);

        boolean removed = userService.removeTrackFromCart(user, track);
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
