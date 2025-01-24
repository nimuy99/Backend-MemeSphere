package com.memesphere.service.user;

import com.memesphere.apipayload.code.status.ErrorStatus;
import com.memesphere.apipayload.exception.GeneralException;
import com.memesphere.converter.UserConverter;
import com.memesphere.domain.User;
import com.memesphere.dto.request.SignInRequest;
import com.memesphere.dto.request.SignUpRequest;
import com.memesphere.dto.response.AuthUserInfoResponse;
import com.memesphere.dto.response.LoginResponse;
import com.memesphere.jwt.TokenProvider;
import com.memesphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;
    private final TokenProvider tokenProvider;

    public void handleUserRegistration(SignUpRequest signUpRequest) {
        User existingUser = userRepository.findByEmail(signUpRequest.getEmail()).orElse(null);

        if (existingUser != null) {
            throw new GeneralException(ErrorStatus.USER_ALREADY_EXISTS);
        }

        if (checkNicknameDuplicate(signUpRequest.getNickname())) {
            throw new GeneralException(ErrorStatus.NICKNAME_ALREADY_EXISTS);
        }

        User newUser = UserConverter.toAuthUser(signUpRequest, passwordEncoder);
        userServiceImpl.save(newUser);
    }

    public LoginResponse handleUserLogin(SignInRequest signInRequest) {
        User existingUser = userRepository.findByEmail(signInRequest.getEmail()).orElse(null);
        String accessToken;

        if (existingUser != null) {

            checkPassword(existingUser, signInRequest.getPassword());

            // 기존 유저가 존재하는 경우 Authentication 객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(existingUser.getEmail(), null, new ArrayList<>());

            accessToken = tokenProvider.createAccessToken(existingUser.getEmail(), authentication);
            String refreshToken = tokenProvider.createRefreshToken(existingUser.getEmail());

            existingUser.setAccessToken(accessToken);
            existingUser.setRefreshToken(refreshToken);
            userRepository.save(existingUser);
            return new LoginResponse(accessToken, refreshToken);
        } else {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }
    }

    public void checkPassword(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_NOT_MATCH);
        }
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }
}
