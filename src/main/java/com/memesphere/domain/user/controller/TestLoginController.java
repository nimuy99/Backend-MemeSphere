package com.memesphere.domain.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class TestLoginController {

    @Value("${testLoginUrl}")
    private String testLoginUrl;

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("testLoginUrl", testLoginUrl);
        return "login";
    }
}
