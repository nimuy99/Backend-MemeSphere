package com.memesphere.domain.user.service;

import com.memesphere.domain.user.entity.User;

public interface UserService {
    User findByLoginId(Long loginId);
}
