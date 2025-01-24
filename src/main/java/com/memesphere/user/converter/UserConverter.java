package com.memesphere.user.converter;

import com.memesphere.user.domain.User;
import com.memesphere.user.domain.SocialType;
import com.memesphere.user.domain.UserRole;
import com.memesphere.user.dto.request.SignUpRequest;
import com.memesphere.user.dto.response.KakaoUserInfoResponse;
import com.memesphere.user.dto.response.TokenResponse;
import com.memesphere.user.dto.response.UserInfoResponse;
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

    public static UserInfoResponse toUserInfo(User user) {
        return UserInfoResponse.builder()
                .loginId(user.getLoginId())
                .nickname(user.getNickname())
                .build();
    }
}

