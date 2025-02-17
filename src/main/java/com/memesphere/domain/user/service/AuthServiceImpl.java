package com.memesphere.domain.user.service;

import com.memesphere.domain.user.converter.UserConverter;
import com.memesphere.domain.user.dto.response.LoginResponse;
import com.memesphere.domain.user.entity.User;
import com.memesphere.domain.user.repository.UserRepository;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.global.jwt.TokenProvider;
import com.memesphere.domain.user.dto.request.SignInRequest;
import com.memesphere.domain.user.dto.request.SignUpRequest;
import com.memesphere.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    public void handleUserRegistration(SignUpRequest signUpRequest) {
        User existingUser = userRepository.findByEmail(signUpRequest.getEmail()).orElse(null);

        if (existingUser != null) {
            throw new GeneralException(ErrorStatus.USER_ALREADY_EXISTS);
        }

        if (checkNicknameDuplicate(signUpRequest.getNickname())) {
            throw new GeneralException(ErrorStatus.NICKNAME_ALREADY_EXISTS);
        }

        User newUser = UserConverter.toAuthUser(signUpRequest, passwordEncoder);
        userRepository.save(newUser);
    }

    public LoginResponse handleUserLogin(SignInRequest signInRequest) {
        User existingUser = userRepository.findByEmail(signInRequest.getEmail()).orElse(null);
        String accessToken;

        if (existingUser != null) {

            checkPassword(existingUser, signInRequest.getPassword());

            accessToken = tokenProvider.createAccessToken(existingUser.getEmail(), existingUser.getLoginId());
            String refreshToken = tokenProvider.createRefreshToken(existingUser.getEmail());

            String nickname = existingUser.getNickname();

            userRepository.save(existingUser);
            // 로그인 시 refreshToken을 redis에 저장
            redisService.setValue(existingUser.getEmail(), refreshToken, 1000 * 60 * 60 * 24 * 7L);

            return new LoginResponse(accessToken, refreshToken, nickname);
        } else {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }
    }

    public void handleUserLogout(String token, User existingUser) {

        if (existingUser != null) {

            /*
            로그아웃 시 refreshToken을 redis에서 삭제하고 accessToken을 redis에 저장
            */
            redisService.deleteValue(existingUser.getEmail());
            redisService.setValue(token, "logout", tokenProvider.getExpirationTime(token));

        } else {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }
    }

    public LoginResponse reissueAccessToken(String refreshToken, User existingUser) {

        if (existingUser == null) {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }

        /*
        validateToken이 false 반환할 경우(로그인 시 refreshToken은 redis에 저장됨. . redis에 없으면 INVALID_TOKEN)
         */
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }

        String email = existingUser.getEmail();
        String redisRefreshToken = redisService.getValue(email);

        if (StringUtils.isEmpty(refreshToken) || StringUtils.isEmpty(redisRefreshToken) || !redisRefreshToken.equals(refreshToken)) {
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }

        return tokenProvider.reissue(existingUser, refreshToken);
    }

    public void checkPassword(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_NOT_MATCH);
        }
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    public String getTmpPassword() {
        char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String pwd = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합
        int idx = 0;
        for(int i = 0; i < 10; i++){
            idx = (int) (charSet.length * Math.random());
            pwd += charSet[idx];
        }

        return pwd;
    }

    @Transactional
    public void updatePassword(String tmpPassword, String memberEmail) {
        String encryptPassword = passwordEncoder.encode(tmpPassword);
        User existingUser = userRepository.findByEmail(memberEmail).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        existingUser.updatePassword(encryptPassword);
    }
}
