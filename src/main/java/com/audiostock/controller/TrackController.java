package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.StatusService;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.TrackIsNotActiveException;
import com.audiostock.service.exceptions.TrackNotFoundException;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/track")
public class TrackController {

    private final UserService userService;
    private final TrackService trackService;
    private final StatusService statusService;

    public TrackController(UserService userService, TrackService trackService, StatusService statusService) {
        this.userService = userService;
        this.trackService = trackService;
        this.statusService = statusService;
    }

    // Representation

    @GetMapping("/{trackId}")
    public String getTrack(@PathVariable Long trackId, Model model, Principal principal)
            throws TrackNotFoundException, TrackIsNotActiveException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        if (!track.isActive()) {
            if (user == null) {
                throw new TrackIsNotActiveException(String.valueOf(track.getId()));
            }

            if (!statusService.isModerator(user)) {
                throw new TrackIsNotActiveException(String.valueOf(track.getId()));
            }
        }
        model.addAttribute("track", track);

        if (user != null) {
            // Printing username in the header
            model.addAttribute("logged", true);
            model.addAttribute("user", user);

            // Printing track buttons
            model.addAttribute("carted", trackService.isInCart(track, user));
            model.addAttribute("purchaced", trackService.isPurchased(track, user));
            model.addAttribute("stared", trackService.isInFavorite(track, user));
        }else{
            model.addAttribute("carted", false);
            model.addAttribute("purchaced", false);
            model.addAttribute("stared", false);
        }

        return "track";
    }


    // Buttons
    @PostMapping("/{trackId}/addToFavorite")
    public String addTrackToFavorite(@PathVariable Long trackId, Principal principal, @RequestHeader String referer)
            throws TrackNotFoundException, TrackIsNotActiveException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        checkTrackIsActive(track);

        boolean added = userService.addTrackToFavorite(user, track);
        System.err.println(added);
        return "redirect:" + referer;
    }

    @PostMapping("/{trackId}/removeFromFavorite")
    public String removeTrackFromFavorite(@PathVariable Long trackId, Principal principal, @RequestHeader String referer)
            throws TrackNotFoundException, TrackIsNotActiveException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        checkTrackIsActive(track);

        boolean removed = userService.removeTrackFromFavorites(user, track);
        return "redirect:" + referer;
    }

    @PostMapping("/{trackId}/addToCart")
    public String addTrackToCart(@PathVariable Long trackId, Principal principal, @RequestHeader String referer)
            throws TrackNotFoundException, TrackIsNotActiveException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        checkTrackIsActive(track);

        boolean added = userService.addTrackToCart(user, track);
        return "redirect:" + referer;
    }

    @PostMapping("/{trackId}/removeFromCart")
    public String removeTrackFromCart(@PathVariable Long trackId, Principal principal, @RequestHeader String referer)
            throws TrackNotFoundException, TrackIsNotActiveException {
        Track track = trackService.getTrackById(trackId);
        User user = Utils.getUserFromPrincipal(principal, userService);

        checkTrackIsActive(track);

        boolean removed = userService.removeTrackFromCart(user, track);
        return "redirect:" + referer;
    }

    /**
     * Запрещает производить действия с неактивным треком
     * @param track неактивный(?) трек
     * @throws TrackIsNotActiveException если трек неактивен
     */
    private void checkTrackIsActive(Track track) throws TrackIsNotActiveException {
        if (!track.isActive()) {
            throw new TrackIsNotActiveException(String.valueOf(track.getId()));
        }
    }

}
