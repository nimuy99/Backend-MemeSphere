package com.memesphere.user.service;

import com.memesphere.user.domain.User;

public interface UserService {
    User findByLoginId(Long loginId);
    void save(User user);
}
