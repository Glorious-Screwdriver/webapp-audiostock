package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.UploadRequest;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UploadRequestService;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UploadRequestNotFoundException;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/moderation")
public class ModeratorController {

    UploadRequestService uploadRequestService;
    UserService userService;
    TrackService trackService;

    public ModeratorController(UploadRequestService uploadRequestService, UserService userService, TrackService trackService) {
        this.uploadRequestService = uploadRequestService;
        this.userService = userService;
        this.trackService = trackService;
    }

    @GetMapping
    public String moderation(Principal principal) {
        User moderator = Utils.getUserFromPrincipal(principal, userService);

        List<UploadRequest> requests = uploadRequestService.getRequestsByModerator(moderator);

        Map<Track, UploadRequest> map = new HashMap<>();
        for (UploadRequest request : requests) {
            map.put(request.getTrack(), request); // можно request тут заменить сразу на requestId, если удобнее
        }

        //TODO moderation view
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{uploadRequestId}")
    public String process(Principal principal,
                          @PathVariable Long uploadRequestId,
                          @RequestParam boolean decision,
                          @RequestParam String rejectionReason,
                          @RequestHeader String referer) // тут скорее всего не @RequestParam - все зависит от того, как ты сделаешь меню ввода текста
            throws UploadRequestNotFoundException {
        User moderator = Utils.getUserFromPrincipal(principal, userService);
        UploadRequest uploadRequest = uploadRequestService.getRequest(uploadRequestId);

        if (uploadRequest.getModerator().equals(moderator)) {
            if (decision) {
                uploadRequestService.approveRequest(uploadRequestId);
            } else {
                uploadRequestService.declineRequest(uploadRequestId, rejectionReason);
            }
        }

        return "redirect:" + referer;
    }

    @ExceptionHandler(UploadRequestNotFoundException.class)
    public String uploadRequestNotFound(UploadRequestNotFoundException e) {
        //TODO uploadRequestNotFound view
        throw new UnsupportedOperationException(e);
    }

}
