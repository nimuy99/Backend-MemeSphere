package com.memesphere.service.user;

import com.memesphere.domain.User;
import com.memesphere.dto.response.UserInfoResponse;

public interface UserService {
    User findByLoginId(Long loginId);
    void save(User user);
    UserInfoResponse getUserInfo(String token);
}
