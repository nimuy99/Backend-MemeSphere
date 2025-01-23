package com.memesphere.domain.user.service;

import com.memesphere.domain.user.converter.UserConverter;
import com.memesphere.domain.user.entity.User;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.domain.user.dto.response.UserInfoResponse;
import com.memesphere.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User findByLoginId(Long loginId) {
        return userRepository.findByLoginId(loginId).orElse(null);
    }

    public void save(User user){
        userRepository.save(user);
    }

    @Transactional
    public UserInfoResponse getUserInfo(String token) {
        User user = userRepository.findByAccessToken(token)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        return UserConverter.toUserInfo(user);
    }
}