package com.audiostock.controller.author;

import com.audiostock.entities.Track;
import com.audiostock.entities.UploadRequest;
import com.audiostock.entities.User;
import com.audiostock.service.UploadRequestService;
import com.audiostock.service.UserService;
import com.audiostock.service.reports.ChangeProfileInfoReport;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileEditController {

    UserService userService;
    UploadRequestService uploadRequestService;

    public ProfileEditController(UserService userService, UploadRequestService uploadRequestService) {
        this.userService = userService;
        this.uploadRequestService = uploadRequestService;
    }

    // Representation

    @GetMapping
    public String profile(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);
        model.addAttribute("user", user);
        return "profile-edit";
    }

    @GetMapping("/releases")
    public String releases(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        List<Track> releasesActive = userService.getActiveReleasesSortedByName(user);
        List<Track> releasesInactive = userService.getInactiveReleasesSortedByName(user);
        List<Track> pending = new ArrayList<>(uploadRequestService.getRequestedTracksByAuthor(user));
//        List<Track> declined = new ArrayList<>(uploadRequestService.getDeclinedTracksByAuthor(user));

        List<UploadRequest> requests = uploadRequestService.getDeclinedRequestsByAuthor(user);

        Map<Track, String> declined = new HashMap<>();
        for (UploadRequest request : requests) {
            declined.put(request.getTrack(), request.getRejectionReason());
        }

        model.addAttribute("user", user);
        model.addAttribute("rel", true);
        model.addAttribute("releases", releasesActive);
        model.addAttribute("inactive", releasesInactive);
        model.addAttribute("pending", pending);
        model.addAttribute("declined", declined);

        return "profile-edit";
    }

    // Editing profile info

    @PostMapping(consumes = "multipart/form-data")
    public String changeAvatar(Principal principal, @RequestParam("avatar") MultipartFile avatar, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        // Смена аватара
        final boolean successful = userService.changeProfileAvatar(user, avatar);

        if (!successful) {
            model.addAttribute("message", "Во время загрузки файла произошла ошибка!");
        }
        model.addAttribute("user", user);

        return "profile-edit";
    }

    @PostMapping(params = "username")
    public String changeUsername(Principal principal, @RequestParam String username, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        final ChangeProfileInfoReport report = userService.changeUsername(user, username);
        if (report.isSuccessful()) {
            return "redirect:/logout";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("message", report.getMessage());
            return "profile-edit";
        }
    }

    @PostMapping(params = {"firstname", "lastname", "middlename"})
    public String changeFullName(Principal principal, Model model,
                                 @RequestParam String firstname,
                                 @RequestParam String lastname,
                                 @RequestParam String middlename) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        // Изменение полного имени пользователя
        final ChangeProfileInfoReport report = userService.changeFullName(
                user, firstname, lastname, middlename
        );

        if (!report.isSuccessful()) {
            model.addAttribute("message", report.getMessage());
        }
        model.addAttribute("user", user);

        return "profile-edit";
    }

    @PostMapping(params = {"old-password", "new-password", "repeat"})
    public String changePassword(Principal principal, Model model,
                                 @RequestParam("old-password") String oldPassword,
                                 @RequestParam("new-password") String newPassword,
                                 @RequestParam("repeat") String newPasswordAgain) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        // Изменение пароля пользователя
        final ChangeProfileInfoReport report = userService.changePassword(
                user, oldPassword, newPassword, newPasswordAgain
        );

        if (!report.isSuccessful()) {
            model.addAttribute("message", report.getMessage());
        }
        model.addAttribute("user", user);

        return "profile-edit";
    }

    @PostMapping(params = "bio")
    public String changePassword(Principal principal, Model model, @RequestParam("bio") String biography) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        // Изменение биографии пользователя
        final ChangeProfileInfoReport report = userService.changeBiography(user, biography);

        if (!report.isSuccessful()) {
            model.addAttribute("message", report.getMessage());
        }
        model.addAttribute("user", user);

        return "profile-edit";
    }

}
