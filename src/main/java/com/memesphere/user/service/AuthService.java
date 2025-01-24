package com.memesphere.user.service;

import com.memesphere.user.dto.request.SignInRequest;
import com.memesphere.user.dto.request.SignUpRequest;
import com.memesphere.user.dto.response.LoginResponse;

public interface AuthService {
    void handleUserRegistration(SignUpRequest signUpRequest);
    LoginResponse handleUserLogin(SignInRequest signInRequest);
}
