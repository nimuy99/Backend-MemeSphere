package com.memesphere.domain.user.controller;

import com.memesphere.domain.user.dto.request.SignInRequest;
import com.memesphere.domain.user.dto.request.SignUpRequest;
import com.memesphere.domain.user.dto.response.TokenResponse;
import com.memesphere.domain.user.dto.response.KakaoUserInfoResponse;
import com.memesphere.domain.user.service.AuthServiceImpl;
import com.memesphere.domain.user.service.KakaoServiceImpl;
import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.domain.user.dto.response.LoginResponse;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.global.jwt.JwtAuthenticationFilter;
import com.memesphere.global.jwt.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @PostMapping("/login/oauth2/kakao")
    @Operation(summary = "카카오 로그인/회원가입 API")
    public ApiResponse<LoginResponse> callback(@RequestParam("code") String code) throws IOException {
        TokenResponse kakaoTokenResponse = kakaoServiceImpl.getAccessTokenFromKakao(code);
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoServiceImpl.getUserInfo(kakaoTokenResponse.getAccessToken());
        LoginResponse loginResponse = kakaoServiceImpl.handleUserLogin(kakaoUserInfoResponse);

        return ApiResponse.onSuccess(loginResponse);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "일반 회원가입 API")
    public ApiResponse<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        authServiceImpl.handleUserRegistration(signUpRequest);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "일반 로그인 API")
    public ApiResponse<LoginResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        LoginResponse loginResponse = authServiceImpl.handleUserLogin(signInRequest);
        return ApiResponse.onSuccess(loginResponse);
    }

    @PostMapping("/sign-out")
    @Operation(summary = "일반/카카오/구글 로그아웃 API", security = @SecurityRequirement(name = "JWT Authentication"))
    public ApiResponse<?> signOut(HttpServletRequest request) {
        String refreshToken = jwtAuthenticationFilter.resolveToken(request);
        String accessToken = jwtAuthenticationFilter.resolveToken(request);

        if(refreshToken==null || accessToken==null){
            throw new GeneralException(ErrorStatus.TOKEN_NOT_FOUND);
        }

        authServiceImpl.handleUserLogout(accessToken, refreshToken);
        return ApiResponse.onSuccess("로그아웃이 성공적으로 처리되었습니다.");
    }
}
