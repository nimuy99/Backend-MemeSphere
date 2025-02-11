package com.memesphere.domain.user.service;

import com.memesphere.domain.user.dto.response.GoogleUserInfoResponse;
import com.memesphere.domain.user.dto.response.LoginResponse;
import com.memesphere.domain.user.dto.response.TokenResponse;

public interface GoogleService {
    TokenResponse getAccessTokenFromGoogle(String code, String redirectUri);
    GoogleUserInfoResponse getUserInfo(String accessToken);
    LoginResponse handleUserLogin(GoogleUserInfoResponse userInfo);
}
