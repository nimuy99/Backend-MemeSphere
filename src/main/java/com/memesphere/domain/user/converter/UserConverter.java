package com.memesphere.domain.user.converter;

import com.memesphere.domain.user.dto.request.SignUpRequest;
import com.memesphere.domain.user.entity.SocialType;
import com.memesphere.domain.user.entity.User;
import com.memesphere.domain.user.dto.response.TokenResponse;
import com.memesphere.domain.user.dto.response.LoginResponse;
import com.memesphere.domain.user.dto.response.KakaoUserInfoResponse;
import com.memesphere.domain.user.entity.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class UserConverter {

    // 카카오 로그인 유저
    public static User toKakaoUser(KakaoUserInfoResponse kakaoUserInfoResponse) {
        return User.builder()
                .loginId(kakaoUserInfoResponse.getId())
                .nickname(kakaoUserInfoResponse.getKakaoUserInfo().getNickname())
                .email(kakaoUserInfoResponse.getKakaoUserInfo().getEmail())
                .socialType(SocialType.KAKAO)
                .userRole(UserRole.USER)
                .build();
    }

    public static User toUpdatedKakaoUser(KakaoUserInfoResponse kakaoUserInfoResponse, TokenResponse tokenResponse) {
        return User.builder()
                .loginId(kakaoUserInfoResponse.getId())
                .nickname(kakaoUserInfoResponse.getKakaoUserInfo().getNickname())
                .email(kakaoUserInfoResponse.getKakaoUserInfo().getEmail())
                .socialType(SocialType.KAKAO)
                .userRole(UserRole.USER)
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .build();
    }

    // 일반 로그인 유저
    public static User toAuthUser(SignUpRequest signUpRequest, PasswordEncoder passwordEncoder) {
        return User.builder()
                .loginId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .nickname(signUpRequest.getNickname())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .socialType(SocialType.GENERAL)
                .userRole(UserRole.USER)
                .build();
    }

    public static LoginResponse toLoginResponse(String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}

