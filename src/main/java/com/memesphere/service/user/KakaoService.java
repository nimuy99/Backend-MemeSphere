package com.memesphere.service.user;

import com.memesphere.dto.user.response.KakaoTokenResponseDTO;
import com.memesphere.dto.user.response.KakaoUserInfoResponseDTO;
import com.memesphere.dto.user.response.UserResponseDTO;

public interface KakaoService {
    // KakaoTokenResponseDTO getAccessTokenFromKakao(String code);
    String getAccessTokenFromKakao(String code);
    KakaoUserInfoResponseDTO getUserInfo(String accessToken);
    void handleUserRegistration(KakaoUserInfoResponseDTO userInfo, KakaoTokenResponseDTO kakaoTokenResponseDTO);
    UserResponseDTO.LoginResult handleUserLogin(KakaoUserInfoResponseDTO userInfo);
}
