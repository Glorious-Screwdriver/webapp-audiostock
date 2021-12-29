package com.audiostock.controller.author;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.UploadRequestService;
import com.audiostock.service.UserService;
import com.audiostock.service.util.ChangeProfileInfoReport;
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
import java.util.List;

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
    public String profile(Principal principal) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        //TODO profile view
        throw new UnsupportedOperationException("/profile view is not supported");
    }

    @GetMapping("/releases")
    public String releases(Principal principal) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        List<Track> releases = new ArrayList<>(user.getReleases());
        List<Track> pending = new ArrayList<>(uploadRequestService.getRequestedTracksByAuthor(user));
        List<Track> declined = new ArrayList<>(uploadRequestService.getDeclinedTracksByAuthor(user));

        //TODO releases view
        throw new UnsupportedOperationException("/profile/releases/releases view is not supported");
    }

    // Editing

    /* Мне кажется логично сделать следующим образом:
    * будет 2 формы - для смены публичных данных автора (полное имя и биография),
    * и для смены пароля. Каждый метод принимает определенное количество параметров,
    * которые приходят из одной из форм */

    @PostMapping(params = {"firstname", "lastname", "middlename", "biography"})
    public String changeAuthorInfo(Principal principal, Model model,
                              @RequestParam String firstname,
                              @RequestParam String lastname,
                              @RequestParam String middlename,
                              @RequestParam String biography) {
        User user = Utils.getUserFromPrincipal(principal, userService);
        final ChangeProfileInfoReport report = userService.changeProfileInfo(
                user, firstname, lastname, middlename, biography
        );

        if (!report.isSuccessful()) {
            model.addAttribute("message", report.getFailureReason());
        }

        //TODO profile view
        throw new UnsupportedOperationException("/profile view is not supported");
    }

    @PostMapping(params = {"oldPassword", "newPassword", "newPasswordAgain"})
    public String changePassword(Principal principal, Model model,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String newPasswordAgain) {
        User user = Utils.getUserFromPrincipal(principal, userService);
        final ChangeProfileInfoReport report = userService.changePassword(
                user, oldPassword, newPassword, newPasswordAgain
        );

        if (!report.isSuccessful()) {
            model.addAttribute("message", report.getFailureReason());
        }

        //TODO profile view
        throw new UnsupportedOperationException("/profile view is not supported");
    }

    @PostMapping(params = "file")
    public String changeAvatar(Principal principal, @RequestParam MultipartFile file, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);
        final boolean successful = userService.changeProfileAvatar(user, file);

        if (!successful) {
            model.addAttribute("message", "Image upload error");
        }

        //TODO profile view
        throw new UnsupportedOperationException("/profile view is not supported");
    }

}
