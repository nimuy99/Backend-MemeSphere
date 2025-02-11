package com.memesphere.domain.user.service;

import com.memesphere.domain.user.dto.request.SignInRequest;
import com.memesphere.domain.user.dto.request.SignUpRequest;
import com.memesphere.domain.user.dto.response.LoginResponse;
import com.memesphere.domain.user.entity.User;

public interface AuthService {
    void handleUserRegistration(SignUpRequest signUpRequest);
    LoginResponse handleUserLogin(SignInRequest signInRequest);
    void checkPassword(User user, String password);
    boolean checkNicknameDuplicate(String nickname);
}
