package com.memesphere.domain.user.service;

import com.memesphere.domain.user.entity.User;

public interface UserService {
    User findByLoginId(Long loginId);
    public String getTmpPassword();
    public void updateTmpPassword(String tmpPassword, String memberEmail);
}
