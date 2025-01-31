package com.memesphere.domain.user.service;

import com.memesphere.domain.user.entity.User;
import com.memesphere.domain.user.dto.response.UserInfoResponse;

public interface UserService {
    User findByLoginId(Long loginId);
    void save(User user);
}
