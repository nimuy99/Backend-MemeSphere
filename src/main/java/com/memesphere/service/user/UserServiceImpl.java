package com.memesphere.service.user;

import com.memesphere.converter.UserConverter;
import com.memesphere.domain.User;
import com.memesphere.dto.response.LoginResponse;
import com.memesphere.dto.response.UserInfoResponse;
import com.memesphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User findBySocialId(Long socialId) {
        return userRepository.findBySocialId(socialId).orElse(null);
    }

    public User findById(Long Id) {
        return userRepository.findById(String.valueOf(Id)).orElse(null);
    }

    public void save(User user){
        userRepository.save(user);
    }

    @Transactional
    public UserInfoResponse getUserInfo(String token) {
        User user = userRepository.findByAccessToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        return UserConverter.toUserInfo(user);
    }
}
