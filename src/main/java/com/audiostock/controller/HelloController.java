package com.audiostock.controller;

import com.audiostock.entities.User;
import com.audiostock.repos.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Controller
public class HelloController {

    UserRepo userRepo;

    public HelloController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String index(){
        return "index";
    }

    // Register

    @GetMapping("/register")
    public String getReg() {
        System.err.println("getRegt");
        return "register";
    }

    @PostMapping("/register")
    public String postReg(@RequestParam String login,
                          @RequestParam String password,
                          @RequestParam String repeat,
                          Map<String, Object> model) {
        System.err.println("it started");
        if (!password.equals(repeat)) {
            System.err.println("don't match");
            model.put("message", "Passwords don't match");
            return "register";
        }

        final Optional<User> userWithSameName = userRepo.findByLogin(login);
        if (userWithSameName.isEmpty()) {
            System.err.println("already taken");
            model.put("message", "Login is already taken");
            return "register";
        }

        userRepo.save(new User(login, password));

        System.err.println("postReg");

        return "redirect:/";
    }
}
