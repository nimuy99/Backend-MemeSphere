package com.memesphere.user.controller;

import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.user.dto.response.KakaoTokenResponse;
import com.memesphere.user.dto.response.LoginResponse;
import com.memesphere.user.dto.response.KakaoUserInfoResponse;
import com.memesphere.user.service.KakaoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name="회원", description = "회원 관련  API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final KakaoServiceImpl kakaoServiceImpl;

    @PostMapping("/login/oauth2/kakao")
    @Operation(summary = "카카오 로그인 API")
    public ApiResponse<LoginResponse> callback(@RequestParam("code") String code) throws IOException {
        KakaoTokenResponse kakaoTokenResponse = kakaoServiceImpl.getAccessTokenFromKakao(code);
        KakaoUserInfoResponse userInfo = kakaoServiceImpl.getUserInfo(kakaoTokenResponse.getAccessToken());
        LoginResponse loginResponse = kakaoServiceImpl.handleUserLogin(userInfo);

        return ApiResponse.onSuccess(loginResponse);
    }
}
