package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.TrackNotFoundException;
import com.audiostock.service.exceptions.UserNotFoundException;
import com.audiostock.service.exceptions.UserNotLoggedInException;
import com.audiostock.service.util.Utils;
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
    public String trackNotFound(TrackNotFoundException e) {
        e.printStackTrace();

        //TODO trackNotFound view
        throw new UnsupportedOperationException(e);
    }

    // Buttons

    @PostMapping("/{trackId}/addToFavorite")
    public void addTrackToFavorite(@PathVariable Long trackId, Principal principal)
            throws TrackNotFoundException, UserNotLoggedInException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        boolean added = userService.addTrackToFavorite(user, track);
    }

    @PostMapping("/{trackId}/removeFromFavorite")
    public void removeTrackFromFavorite(@PathVariable Long trackId, Principal principal)
            throws TrackNotFoundException, UserNotLoggedInException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        boolean removed = userService.removeTrackFromFavorites(user, track);
    }

    @PostMapping("/{trackId}/addToCart")
    public void addTrackToCart(@PathVariable Long trackId, Principal principal)
            throws TrackNotFoundException, UserNotLoggedInException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        boolean added = userService.addTrackToCart(user, track);
    }

    @PostMapping("/{trackId}/removeFromCart")
    public void removeTrackFromCart(@PathVariable Long trackId, Principal principal)
            throws TrackNotFoundException, UserNotLoggedInException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        boolean removed = userService.removeTrackFromCart(user, track);
    }

}
