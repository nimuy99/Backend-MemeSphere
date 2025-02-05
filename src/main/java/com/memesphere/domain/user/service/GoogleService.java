package com.memesphere.domain.user.service;

import com.memesphere.domain.user.dto.response.GoogleUserInfoResponse;
import com.memesphere.domain.user.dto.response.LoginResponse;
import com.memesphere.domain.user.dto.response.TokenResponse;

public interface GoogleService {
    TokenResponse getAccessTokenFromGoogle(String code);
    GoogleUserInfoResponse getUserInfo(String accessToken);
    void handleUserRegistration(GoogleUserInfoResponse userInfo, TokenResponse kakaoTokenResponse);
    LoginResponse handleUserLogin(GoogleUserInfoResponse userInfo);
}
