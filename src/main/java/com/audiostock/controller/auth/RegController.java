package com.audiostock.controller.auth;

import com.audiostock.service.UserService;
import com.audiostock.service.reports.RegisterReport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegController {

    private final UserService userService;

    public RegController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getReg() {
        return "register";
    }

    @PostMapping("/register")
    public String postReg(String username, String password, String repeat, Model model) {
        RegisterReport registerReport = userService.register(username, password, repeat);

        if (!registerReport.isSuccessful()) {
            model.addAttribute("message", registerReport.getMessage());
            return "register";
        }

        return "redirect:/";
    }
}
