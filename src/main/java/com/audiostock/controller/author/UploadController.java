package com.audiostock.controller.author;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UploadRequestService;
import com.audiostock.service.UserService;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/profile/releases/upload")
public class UploadController {

    UploadRequestService uploadRequestService;
    UserService userService;
    TrackService trackService;

    public UploadController(UploadRequestService uploadRequestService, UserService userService, TrackService trackService) {
        this.uploadRequestService = uploadRequestService;
        this.userService = userService;
        this.trackService = trackService;
    }

    @GetMapping
    public String uploadPage(Principal principal) {
        //TODO upload view
        throw new UnsupportedOperationException("/profile/releases/upload view is not supported");
    }

    @PostMapping(params = "file")
    public String upload(Principal principal, Model model,
                         @RequestParam String name,
                         @RequestParam String description,
                         @RequestParam long price,
                         @RequestParam String genre,
                         @RequestParam String mood,
                         @RequestParam long bpm,
                         @RequestParam MultipartFile file) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        final Track track = trackService.addTrack(user, name, description, price, genre, mood, bpm, file);
        if (track != null) {
            uploadRequestService.addRequest(user, track);
        } else {
            model.addAttribute("message", "Track upload error");
        }

        //TODO releases view
        throw new UnsupportedOperationException("/profile/releases view is not supported");
    }

}
