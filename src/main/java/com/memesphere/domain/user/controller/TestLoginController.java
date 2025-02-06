package com.memesphere.domain.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class TestLoginController {

    @Value("${testKakaoLoginUrl}")
    private String testKakaoLoginUrl;

    @Value("${testGoogleLoginUrl}")
    private String testGoogleLoginUrl;

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("testKakaoLoginUrl", testKakaoLoginUrl);
        model.addAttribute("testGoogleLoginUrl", testGoogleLoginUrl);
        return "login";
    }
}
