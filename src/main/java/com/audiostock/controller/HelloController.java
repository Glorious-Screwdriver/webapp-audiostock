package com.audiostock.controller;

import com.audiostock.entities.UserEntity;
import com.audiostock.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
//ToDo
@Controller
public class HelloController {

    UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public HelloController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String index(Principal principal, Model model){
        model.addAttribute("logged",principal!=null);
        if(principal!=null)model.addAttribute("username", principal.getName());
        System.out.println("logged " + (principal!=null));
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        System.err.println("login");
        return "login";
    }
    @GetMapping("/login-error")
    public String login(@RequestParam("error")Optional<Integer> err, Model model){
        System.err.println("errLogin");
        model.addAttribute("loginError",true);
        return "login";
    }
    // Register

    @GetMapping("/register")
    public String getReg() {
        System.err.println("getRegt");
        return "register";
    }

    @PostMapping("/register")
    public String postReg(String username, String password, String repeat, Map<String, Object> model) {
        System.err.println("postReg");
        System.out.println(username+" "+password);
        if (!password.equals(repeat)) {
            model.put("message", "Passwords don't match");
            return "register";
        }

        final Optional<UserEntity> userWithSameName = userRepo.findByLogin(username);
        if (userWithSameName.isPresent()) {
            model.put("message", "Login is already taken");
            return "register";
        }

        userRepo.save(new UserEntity(username, encoder.encode(password)));

        return "redirect:/";
    }
}
