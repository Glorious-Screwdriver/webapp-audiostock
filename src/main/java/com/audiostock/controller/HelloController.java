package com.audiostock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class HelloController {

    @GetMapping("/")
    public String index(Principal principal, Model model) {
        System.err.println("GET /"); //TODO triple GET
//        model.addAttribute("logged", principal != null);
//        if (principal != null) model.addAttribute("username", principal.getName());
//        System.out.println("logged " + (principal != null));
        return "index";
    }

    // Login

    // Этот маппинг просто предоставляет View для /login
    @GetMapping("/login")
    public String getLogin() {
        System.err.println("GET /login");
        return "login";
    }

    // Этот маппинг замещается SpringSecurity
//    @PostMapping("/login")
//    public String postLogin() {
//        System.err.println("POST /login");
//        return "login";
//    }

//    @GetMapping("/login-error")
//    public String login(@RequestParam("error") Optional<Integer> err, Model model) {
//        System.err.println("errLogin");
//        model.addAttribute("loginError", true);
//        return "login";
//    }

}
