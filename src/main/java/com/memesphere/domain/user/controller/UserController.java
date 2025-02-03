package com.memesphere.domain.user.controller;

import com.memesphere.domain.user.dto.request.ReissueRequest;
import com.memesphere.domain.user.dto.request.SignInRequest;
import com.memesphere.domain.user.dto.request.SignUpRequest;
import com.memesphere.domain.user.dto.response.TokenResponse;
import com.memesphere.domain.user.dto.response.KakaoUserInfoResponse;
import com.memesphere.domain.user.service.AuthServiceImpl;
import com.memesphere.domain.user.service.KakaoServiceImpl;
import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.domain.user.dto.response.LoginResponse;
import com.memesphere.global.jwt.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
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

        return ApiResponse.onSuccess("회원가입이 완료되었습니다.");
    }

    @PostMapping("/sign-in")
    @Operation(summary = "일반 로그인 API")
    public ApiResponse<LoginResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        LoginResponse loginResponse = authServiceImpl.handleUserLogin(signInRequest);

        return ApiResponse.onSuccess(loginResponse);
    }

    @PostMapping("/sign-out")
    @Operation(summary = "일반/카카오/구글 로그아웃 API")
    public ApiResponse<?> signOut(HttpServletRequest request) {
        String token = jwtAuthenticationFilter.resolveToken(request);
        authServiceImpl.handleUserLogout(token);

        return ApiResponse.onSuccess("로그아웃이 완료되었습니다.");
    }

    @PostMapping("/reissue")
    @Operation(summary = "리프레시 토큰으로 액세스 토큰 재발급 API")
    public ApiResponse<LoginResponse> reissueAccessToken(@RequestBody ReissueRequest reissueRequest) {
        LoginResponse loginResponse = authServiceImpl.reissueAccessToken(reissueRequest.getRefreshToken());

        return ApiResponse.onSuccess(loginResponse);
    }
}
