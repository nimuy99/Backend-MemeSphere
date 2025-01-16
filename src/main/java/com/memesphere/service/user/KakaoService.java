package com.memesphere.service.user;

import com.memesphere.dto.response.LoginResponse;
import com.memesphere.dto.response.KakaoTokenResponse;
import com.memesphere.dto.response.KakaoUserInfoResponse;

public interface KakaoService {
    // KakaoTokenResponseDTO getAccessTokenFromKakao(String code);
    String getAccessTokenFromKakao(String code);
    KakaoUserInfoResponse getUserInfo(String accessToken);
    void handleUserRegistration(KakaoUserInfoResponse userInfo, KakaoTokenResponse kakaoTokenResponse);
    LoginResponse handleUserLogin(KakaoUserInfoResponse userInfo);
}
