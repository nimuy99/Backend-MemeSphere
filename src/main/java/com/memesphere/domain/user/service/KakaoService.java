package com.memesphere.domain.user.service;

import com.memesphere.domain.user.dto.response.LoginResponse;
import com.memesphere.domain.user.dto.response.KakaoTokenResponse;
import com.memesphere.domain.user.dto.response.KakaoUserInfoResponse;

public interface KakaoService {
    KakaoTokenResponse getAccessTokenFromKakao(String code);
    KakaoUserInfoResponse getUserInfo(String accessToken);
    void handleUserRegistration(KakaoUserInfoResponse userInfo, KakaoTokenResponse kakaoTokenResponse);
    LoginResponse handleUserLogin(KakaoUserInfoResponse userInfo);
}
