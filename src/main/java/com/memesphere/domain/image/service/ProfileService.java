package com.memesphere.domain.image.service;

import com.memesphere.domain.user.entity.User;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    // 프로필 이미지 반환
    public String getProfileImage(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null || customUserDetails.getUser() == null) {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }

        User user = customUserDetails.getUser();
        return user.getProfileImage();
    }

}
