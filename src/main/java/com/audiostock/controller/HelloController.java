package com.audiostock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    @GetMapping("*")
    public String index(){
        return "catal";
    }

    @GetMapping(value = "/login")
    public String hello() {
        System.err.println("login get");
        return "/WEB-INF/jsp/login.html";
    }
    @GetMapping(value = "/register")
    public String reg() {
        System.err.println("reg get");
        return "register";
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request){
        System.out.println(request.getRequestURL());
        System.err.println("login post");
    }
}
