package com.memesphere.service.user;

import com.memesphere.domain.User;
import com.memesphere.dto.response.UserInfoResponse;

public interface UserService {
    User findBySocialId(Long socialId);
    void save(User user);
    UserInfoResponse getUserInfo(String token);
    User findById(Long Id);
}
