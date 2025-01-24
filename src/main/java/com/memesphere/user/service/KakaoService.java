package com.memesphere.user.service;

import com.memesphere.user.dto.response.LoginResponse;
import com.memesphere.user.dto.response.TokenResponse;
import com.memesphere.user.dto.response.KakaoUserInfoResponse;

public interface KakaoService {
    TokenResponse getAccessTokenFromKakao(String code);
    KakaoUserInfoResponse getUserInfo(String accessToken);
    void handleUserRegistration(KakaoUserInfoResponse userInfo, TokenResponse kakaoTokenResponse);
    LoginResponse handleUserLogin(KakaoUserInfoResponse userInfo);
}
