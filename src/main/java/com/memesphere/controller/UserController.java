package com.memesphere.controller;

import com.memesphere.apipayload.ApiResponse;
import com.memesphere.dto.user.response.KakaoTokenResponseDTO;
import com.memesphere.dto.user.response.KakaoUserInfoResponseDTO;
import com.memesphere.dto.user.response.UserResponseDTO;
import com.memesphere.service.user.KakaoServiceImpl;
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
    public ApiResponse<UserResponseDTO.LoginResult> callback(@RequestParam("code") String code) throws IOException {
        KakaoTokenResponseDTO kakaoTokenResponseDTO = kakaoServiceImpl.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDTO userInfo = kakaoServiceImpl.getUserInfo(kakaoTokenResponseDTO.getAccessToken());
        UserResponseDTO.LoginResult loginResult = kakaoServiceImpl.handleUserLogin(userInfo);

        return ApiResponse.onSuccess(loginResult);
    }
}
