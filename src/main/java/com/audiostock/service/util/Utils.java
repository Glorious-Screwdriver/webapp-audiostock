package com.audiostock.service.util;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UserNotFoundException;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static User getUserFromPrincipal(Principal principal, UserService userService) {
        User user = null;
        if (principal != null) {
            try {
                user = userService.getUserByUsername(principal.getName());
            } catch (UserNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        return user;
    }

    public static Map<Track, Boolean[]> getTrackMap(User user, List<Track> tracks, TrackService trackService) {
        Map<Track, Boolean[]> map = new LinkedHashMap<>();

        if (user != null) {
            for (Track track : tracks) {
                map.put(track, new Boolean[]{
                        trackService.isInFavorite(track, user),
                        trackService.isInCart(track, user)
                });
            }
        } else {
            for (Track track : tracks) {
                map.put(track, new Boolean[]{false, false});
            }
        }

        return map;
    }

}
