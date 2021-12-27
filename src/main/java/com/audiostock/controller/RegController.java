package com.audiostock.controller;

import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UsernameIsAlreadyTakenException;
import com.audiostock.service.exceptions.PasswordsDoNotMatchException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegController {

    private UserService userService;

    public RegController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getReg() {
        System.err.println("GET /register");
        return "register";
    }

    @PostMapping("/register")
    public String postReg(String username, String password, String repeat, Model model) {
        System.err.println("POST /register {username:" + username + ", password:" + password + "}");

        try {
            userService.register(username, password, repeat);
        } catch (UsernameIsAlreadyTakenException e) {
            model.addAttribute("message", "Login is already taken");
            return "register";
        } catch (PasswordsDoNotMatchException e) {
            model.addAttribute("message", "Passwords don't match");
            return "register";
        }

        return "redirect:/";
    }
}
