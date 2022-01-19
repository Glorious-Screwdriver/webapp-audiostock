package com.audiostock.controller.author;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.TrackNotFoundException;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/profile/releases")
public class TrackEditController {

    UserService userService;
    TrackService trackService;

    public TrackEditController(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    @GetMapping("/{trackId}")
    public String editTrackPage(Principal principal, @PathVariable long trackId, Model model)
            throws TrackNotFoundException {
        User author = Utils.getUserFromPrincipal(principal, userService);
        Track track = trackService.getTrackById(trackId);

        checkEditingPermission(author, track);

        model.addAttribute("user", author);
        model.addAttribute("track", track);

        //TODO /profile/releases/{trackId} view
        throw new UnsupportedOperationException("/profile/releases/{trackId} view is not supported");
    }

    @PostMapping("/{trackId}")
    public String editTrack(Principal principal, @PathVariable long trackId, Model model,
                            @RequestParam Map<String, String> params) throws TrackNotFoundException {
        User author = Utils.getUserFromPrincipal(principal, userService);
        Track track = trackService.getTrackById(trackId);

        checkEditingPermission(author, track);

        // Проверка на пустые поля
        for (String value : params.values()) {
            if (value == null) {
                model.addAttribute("message", "Необходимо заполнить все поля!");
                model.addAttribute("track", track);

                //TODO /profile/releases/{trackId} view
                throw new UnsupportedOperationException("/profile/releases/{trackId} view is not supported");
            }
        }

        // Изменение сведений о треке
        // Наверно это можно сделать по-другому..?
        track.setBpm(Long.parseLong(params.get("bpm")));
        track.setDescription(params.get("description"));
        track.setGenre(params.get("genre"));
        track.setMood(params.get("mood"));
        track.setName(params.get("name"));
        track.setPrice(Long.parseLong(params.get("price")));

        trackService.editTrack(track);
        return "redirect:/track/" + trackId;
    }

    @PostMapping(value = "/{trackId}", consumes = "multipart/form-data")
    public String changeCover(Principal principal, @PathVariable long trackId, Model model,
                              @RequestParam("cover") MultipartFile cover) throws TrackNotFoundException {
        User author = Utils.getUserFromPrincipal(principal, userService);
        Track track = trackService.getTrackById(trackId);

        checkEditingPermission(author, track);

        final boolean successful = trackService.changeCover(track, cover);
        if (!successful) {
            model.addAttribute("message", "Ошибка при загрузке обложки!");
            model.addAttribute("track", track);

            //TODO /profile/releases/{trackId} view
            throw new UnsupportedOperationException("/profile/releases/{trackId} view is not supported");
        }

        return "redirect:/track/" + trackId;
    }

    @DeleteMapping("/{trackId}")
    public String deleteTrack(Principal principal, @PathVariable long trackId) throws TrackNotFoundException {
        User author = Utils.getUserFromPrincipal(principal, userService);
        Track track = trackService.getTrackById(trackId);

        checkEditingPermission(author, track);

        trackService.deleteTrack(track);
        return "redirect:/profile/releases";
    }

    private void checkEditingPermission(User author, Track track) throws TrackNotFoundException {
        if (!track.getAuthor().equals(author)) throw new TrackNotFoundException("Illegal access to track");
    }

}
