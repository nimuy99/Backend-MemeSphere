package com.memesphere.service.user;

import com.memesphere.domain.User;
import com.memesphere.dto.user.response.UserResponseDTO;

public interface  UserService {
    User findByKakaoId(Long kakaoId);
    void save(User user);
    UserResponseDTO.UserInfo getUserInfo(String token);
}
