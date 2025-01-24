package com.memesphere.user.converter;

import com.memesphere.user.domain.User;
import com.memesphere.user.domain.SocialType;
import com.memesphere.user.dto.response.KakaoTokenResponse;
import com.memesphere.user.dto.response.KakaoUserInfoResponse;
import com.memesphere.user.dto.response.UserInfoResponse;

public class UserConverter {

    public static User createUser(KakaoUserInfoResponse userInfo) {
        return User.builder()
                .loginId(userInfo.getId())
                .nickname(userInfo.getKakaoUserInfo().getNickname())
                .email(userInfo.getKakaoUserInfo().getEmail())
                .socialType(SocialType.KAKAO)
                .build();
    }

    public static User updateUser(KakaoUserInfoResponse userInfo, KakaoTokenResponse kakaoTokenResponse) {
        return User.builder()
                .loginId(userInfo.getId())
                .nickname(userInfo.getKakaoUserInfo().getNickname())
                .email(userInfo.getKakaoUserInfo().getEmail())
                .socialType(SocialType.KAKAO)
                .accessToken(kakaoTokenResponse.getAccessToken())
                .refreshToken(kakaoTokenResponse.getRefreshToken())
                .build();
    }

    public static UserInfoResponse toUserInfo(User user) {
        return UserInfoResponse.builder()
                .loginId(user.getLoginId())
                .nickname(user.getNickname())
                .build();
    }
}

