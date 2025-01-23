package com.memesphere.user.service;

import com.memesphere.user.domain.User;
import com.memesphere.user.dto.response.UserInfoResponse;

public interface UserService {
    User findByLoginId(Long loginId);
    void save(User user);
    UserInfoResponse getUserInfo(String token);
}
