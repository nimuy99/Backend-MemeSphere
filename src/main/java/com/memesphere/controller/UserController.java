package com.memesphere.controller;

import com.memesphere.apipayload.ApiResponse;
import com.memesphere.domain.User;
import com.memesphere.dto.request.SignInRequest;
import com.memesphere.dto.request.SignUpRequest;
import com.memesphere.dto.response.*;
import com.memesphere.service.user.AuthServiceImpl;
import com.memesphere.service.user.KakaoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name="회원", description = "회원 관련  API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final KakaoServiceImpl kakaoServiceImpl;
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login/oauth2/kakao")
    @Operation(summary = "카카오 로그인 API")
    public ApiResponse<LoginResponse> callback(@RequestParam("code") String code) throws IOException {
        TokenResponse kakaoTokenResponse = kakaoServiceImpl.getAccessTokenFromKakao(code);
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoServiceImpl.getUserInfo(kakaoTokenResponse.getAccessToken());
        LoginResponse loginResponse = kakaoServiceImpl.handleUserLogin(kakaoUserInfoResponse);

        return ApiResponse.onSuccess(loginResponse);
    }

    @PostMapping("/signup")
    @Operation(summary = "일반 회원가입 API")
    public ApiResponse<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {

        authServiceImpl.handleUserRegistration(signUpRequest);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/signin")
    @Operation(summary = "일반 로그인 API")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody SignInRequest signInRequest) {
        LoginResponse loginResponse = authServiceImpl.handleUserLogin(signInRequest);
        return ApiResponse.onSuccess(loginResponse);
    }
}
