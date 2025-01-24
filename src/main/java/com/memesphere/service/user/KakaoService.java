package com.memesphere.service.user;

import com.memesphere.dto.response.KakaoUserInfoResponse;
import com.memesphere.dto.response.LoginResponse;
import com.memesphere.dto.response.TokenResponse;
import com.memesphere.dto.response.UserInfoResponse;

public interface KakaoService {
    TokenResponse getAccessTokenFromKakao(String code);
    KakaoUserInfoResponse getUserInfo(String accessToken);
    void handleUserRegistration(KakaoUserInfoResponse userInfo, TokenResponse kakaoTokenResponse);
    LoginResponse handleUserLogin(KakaoUserInfoResponse userInfo);
}
