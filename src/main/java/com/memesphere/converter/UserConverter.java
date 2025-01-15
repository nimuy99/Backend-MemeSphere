package com.memesphere.converter;

import com.memesphere.domain.User;
import com.memesphere.dto.user.response.UserResponseDTO;

public class UserConverter {

    public static UserResponseDTO.UserInfo toUserInfo(User user) {

        return UserResponseDTO.UserInfo.builder()
                .kakaoId(user.getKakaoId())
                .nickname(user.getNickname())
                .build();
    }
}
