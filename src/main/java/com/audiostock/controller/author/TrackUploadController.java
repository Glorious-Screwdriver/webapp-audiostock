package com.audiostock.controller.author;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UploadRequestService;
import com.audiostock.service.UserService;
import com.audiostock.service.reports.TrackUploadReport;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/profile/releases/upload")
public class TrackUploadController {

    UploadRequestService uploadRequestService;
    UserService userService;
    TrackService trackService;

    public TrackUploadController(UploadRequestService uploadRequestService, UserService userService, TrackService trackService) {
        this.uploadRequestService = uploadRequestService;
        this.userService = userService;
        this.trackService = trackService;
    }

    @GetMapping
    public String uploadPage(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);
        model.addAttribute("user", user);

        //TODO upload view
        throw new UnsupportedOperationException("/profile/releases/upload view is not supported");
    }

    @PostMapping(consumes = "multipart/form-data")
    public String upload(Principal principal, Model model, @RequestParam Map<String, String> params,
                         @RequestParam("original") MultipartFile original,
                         @RequestParam("preview") MultipartFile preview,
                         @RequestParam("cover") MultipartFile cover) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        // Проверка на пустые значения
        for (String value : params.values()) {
            if (value == null) {
                model.addAttribute("message", "Необходимо заполнить все поля!");
                model.addAttribute("user", user);

                //TODO upload view
                throw new UnsupportedOperationException("/profile/releases/upload view is not supported");
            }
        }

        // Сохранение трека и запись в бд
        final TrackUploadReport report = trackService.uploadTrack(
                user,
                params.get("username"),
                params.get("description"),
                Long.parseLong(params.get("price")),
                params.get("genre"),
                params.get("mood"),
                Long.parseLong(params.get("genre")),
                original,
                preview,
                cover
        );

        if (report.isSuccessful()) {
            Track track = report.getTrack();
            uploadRequestService.createRequest(user, track);

            //TODO releases view
            throw new UnsupportedOperationException("/profile/releases view is not supported");
        } else {
            model.addAttribute("message", report.getMessage());
            model.addAttribute("user", user);

            //TODO upload view
            throw new UnsupportedOperationException("/profile/releases/upload view is not supported");
        }
    }

}
