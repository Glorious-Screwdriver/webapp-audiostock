package com.audiostock.controller.consumer;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.TrackNotFoundException;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/purchased")
public class PurchasedController {

    UserService userService;
    TrackService trackService;

    public PurchasedController(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    @GetMapping
    public String purchased(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);
        List<Track> tracks = userService.getPurchasedSortedByName(user);

        model.addAttribute("user", user);

        // Track map
        Map<Track, Boolean[]> map = Utils.getTrackMap(user, tracks, trackService);
        model.addAttribute("tracks", map);

        return "purchased";
    }

    @GetMapping("/{trackId}")
    public void download(Principal principal, @PathVariable Long trackId, HttpServletResponse response)
            throws TrackNotFoundException {
        User user = Utils.getUserFromPrincipal(principal, userService);
        Track track = trackService.getTrackById(trackId);

        if (!user.getPurchased().contains(track)) {
            throw new TrackNotFoundException(track, "Illegal download request");
        }

        Path filePath = Paths.get("data/tracks/" + track.getId() + ".mp3");
        if (Files.exists(filePath)) {
            String filename = (track.getAuthor().getLogin() + " - " + track.getName())
                    .replace(" ", "_");

            response.setContentType("audio/mpeg; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", String.format("attachment; filename=%s.mp3",
                    URLEncoder.encode(filename, StandardCharsets.UTF_8)));

            try {
                Files.copy(filePath, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ignored) {

            }
        } else {
            throw new IllegalStateException("There is no file for track: " + track.getId());
        }
    }
}
