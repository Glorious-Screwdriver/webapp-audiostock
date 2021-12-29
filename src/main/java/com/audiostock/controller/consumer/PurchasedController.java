package com.audiostock.controller.consumer;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.UserService;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/purchased")
public class PurchasedController {

    UserService userService;

    public PurchasedController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String purchased(Principal principal) {
        User user = Utils.getUserFromPrincipal(principal, userService);
        List<Track> tracks = new ArrayList<>(user.getPurchased());

        //TODO purchased view
        throw new UnsupportedOperationException("/purchased view is not supported");
    }
}
