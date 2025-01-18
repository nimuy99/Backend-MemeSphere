package com.memesphere.converter;

import com.memesphere.domain.User;
import com.memesphere.dto.response.UserInfoResponse;

public class UserConverter {

    public static UserInfoResponse toUserInfo(User user) {

        return UserInfoResponse.builder()
                .loginId(user.getLoginId())
                .nickname(user.getNickname())
                .build();
    }
}
