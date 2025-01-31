package com.memesphere.domain.user.controller;

import com.memesphere.domain.user.dto.request.SignInRequest;
import com.memesphere.domain.user.dto.request.SignUpRequest;
import com.memesphere.domain.user.dto.response.GoogleUserInfoResponse;
import com.memesphere.domain.user.dto.response.TokenResponse;
import com.memesphere.domain.user.dto.response.KakaoUserInfoResponse;
import com.memesphere.domain.user.service.AuthServiceImpl;
import com.memesphere.domain.user.service.GoogleServiceImpl;
import com.memesphere.domain.user.service.KakaoServiceImpl;
import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.domain.user.dto.response.LoginResponse;
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
    private final GoogleServiceImpl googleServiceImpl;
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login/oauth2/kakao")
    @Operation(summary = "카카오 로그인/회원가입 API")
    public ApiResponse<LoginResponse> kakaoLogin(@RequestParam("code") String code) throws IOException {
        TokenResponse kakaoTokenResponse = kakaoServiceImpl.getAccessTokenFromKakao(code);
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoServiceImpl.getUserInfo(kakaoTokenResponse.getAccessToken());
        LoginResponse loginResponse = kakaoServiceImpl.handleUserLogin(kakaoUserInfoResponse);

        return ApiResponse.onSuccess(loginResponse);
    }

    @PostMapping("/login/oauth2/google")
    @Operation(summary = "구글 로그인/회원가입 API")
    public ApiResponse<LoginResponse> googleLogin(@RequestParam("code") String code) throws IOException {
        TokenResponse googleTokenResponse = googleServiceImpl.getAccessTokenFromGoogle(code);
        GoogleUserInfoResponse googleUserInfoResponse = googleServiceImpl.getUserInfo(googleTokenResponse.getAccessToken());
        LoginResponse loginResponse = googleServiceImpl.handleUserLogin(googleUserInfoResponse);

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
