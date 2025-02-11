package com.memesphere.domain.user.controller;

import com.memesphere.domain.user.dto.request.ReissueRequest;
import com.memesphere.domain.user.dto.request.NicknameRequest;
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
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.global.jwt.CustomUserDetails;
import com.memesphere.global.jwt.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

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
    public ApiResponse<LoginResponse> googleLogin(HttpServletRequest request, @RequestParam("code") String code) throws IOException {
        TokenResponse googleTokenResponse = null;
        String origin = request.getHeader("Origin");

        if (origin.equals("http://localhost:3000")) {
            googleTokenResponse = googleServiceImpl.getAccessTokenFromGoogle(code, "http://localhost:3000/user/login/oauth2/google");
        } else if (origin.equals("http://localhost:8080")) {
            googleTokenResponse = googleServiceImpl.getAccessTokenFromGoogle(code, "http://localhost:8080/user/login/oauth2/google");
        } else if (origin.equals("https://15.164.103.195.nip.io")) {
            googleTokenResponse = googleServiceImpl.getAccessTokenFromGoogle(code, "https://15.164.103.195.nip.io/user/login/oauth2/google");
        }

        GoogleUserInfoResponse googleUserInfoResponse = googleServiceImpl.getUserInfo(googleTokenResponse.getAccessToken());
        LoginResponse loginResponse = googleServiceImpl.handleUserLogin(googleUserInfoResponse);

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
    public ApiResponse<?> signOut(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String token = jwtAuthenticationFilter.resolveToken(request);
        authServiceImpl.handleUserLogout(token, customUserDetails.getUser());

        return ApiResponse.onSuccess("로그아웃이 완료되었습니다.");
    }

    @PostMapping("/reissue")
    @Operation(summary = "리프레시 토큰으로 액세스 토큰 재발급 API")
    public ApiResponse<LoginResponse> reissueAccessToken(@RequestBody ReissueRequest reissueRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }

        LoginResponse loginResponse = authServiceImpl.reissueAccessToken(reissueRequest.getRefreshToken(), customUserDetails.getUser());

        return ApiResponse.onSuccess(loginResponse);
    }

    @PostMapping("/signup/nickname/validate")
    @Operation(summary = "닉네임 중복 확인 API")
    public ApiResponse<?> isNicknameValidate(@RequestBody NicknameRequest nicknameRequest) {
        boolean isDuplicate = authServiceImpl.checkNicknameDuplicate(nicknameRequest.getNickname());

        if (isDuplicate) {
            return ApiResponse.onSuccess("이미 사용 중인 닉네임입니다.");
        } else {
            return ApiResponse.onSuccess("사용 가능한 닉네임입니다.");
        }
    }
}
