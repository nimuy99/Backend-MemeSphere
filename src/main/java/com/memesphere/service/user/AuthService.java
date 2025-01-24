package com.memesphere.service.user;

import com.memesphere.dto.request.SignInRequest;
import com.memesphere.dto.request.SignUpRequest;
import com.memesphere.dto.response.AuthUserInfoResponse;
import com.memesphere.dto.response.LoginResponse;
import com.memesphere.dto.response.UserInfoResponse;

public interface AuthService {
    void handleUserRegistration(SignUpRequest signUpRequest);
    LoginResponse handleUserLogin(SignInRequest signInRequest);
}
