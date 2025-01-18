package com.memesphere.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class TestLoginController {

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
}
