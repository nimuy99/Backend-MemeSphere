package com.memesphere.user.service;

import com.memesphere.user.converter.UserConverter;
import com.memesphere.user.domain.User;
import com.memesphere.user.domain.SocialType;
import com.memesphere.user.dto.response.LoginResponse;
import com.memesphere.user.dto.response.KakaoTokenResponse;
import com.memesphere.user.dto.response.KakaoUserInfoResponse;
import com.memesphere.global.jwt.TokenProvider;
import com.memesphere.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

    private final TokenProvider tokenProvider;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

    @Value("${security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    @Value("${security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    public KakaoTokenResponse getAccessTokenFromKakao(String code) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String uri = UriComponentsBuilder.fromUriString(tokenUri)
                    .queryParam("grant_type", "authorization_code")
                    .queryParam("client_id", clientId)
                    .queryParam("code", code)
                    .toUriString();

            ResponseEntity<KakaoTokenResponse> responseEntity = restTemplate.postForEntity(uri, null, KakaoTokenResponse.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("Error occurred while getting access token from Kakao: ", e);
            throw new RuntimeException("Failed to retrieve access token from Kakao", e);
        }
    }

    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String uri = UriComponentsBuilder.fromUriString(userInfoUri).toUriString();

            ResponseEntity<KakaoUserInfoResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoUserInfoResponse.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("Error occurred while getting user info from Kakao: ", e);
            throw new RuntimeException("Failed to retrieve user info from Kakao", e);
        }
    }

    // 사용자 정보로 회원가입 처리
    public void handleUserRegistration(KakaoUserInfoResponse userInfo, KakaoTokenResponse kakaoTokenResponse) {
        Long loginId = userInfo.getId();
        User existingUser = userServiceImpl.findByLoginId(loginId);

        if (existingUser == null) { // 유저가 존재하지 않으면 회원가입 처리
            User newUser = UserConverter.createUser(userInfo); // 신규 유저 생성
            userServiceImpl.save(newUser);
        } else {
            // 이미 존재하는 경우 토큰을 업데이트
            User updatedUser = UserConverter.updateUser(userInfo, kakaoTokenResponse); // 기존 유저 업데이트
            existingUser.setSocialType(SocialType.KAKAO);
            userServiceImpl.save(updatedUser);
        }
    }

    public LoginResponse handleUserLogin(KakaoUserInfoResponse userInfo) {
        User existingUser = userServiceImpl.findByLoginId(userInfo.getId());
        String accessToken;

        if (existingUser != null) {
            // 기존 유저가 존재하는 경우 Authentication 객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(existingUser.getEmail(), null, new ArrayList<>());

            // 액세스 토큰과 리프레쉬 토큰을 발급
            accessToken = tokenProvider.createAccessToken(existingUser.getEmail(), authentication);
            String refreshToken = tokenProvider.createRefreshToken(existingUser.getEmail());

            // 발급된 토큰을 기존 유저 객체에 업데이트
            existingUser.setAccessToken(accessToken);
            existingUser.setRefreshToken(refreshToken);
            userRepository.save(existingUser);

            return new LoginResponse(accessToken, refreshToken);
        } else {
            User newUser = UserConverter.createUser(userInfo); // 신규 유저 생성
            newUser = userRepository.save(newUser);

            Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getEmail(), null, new ArrayList<>());

            accessToken = tokenProvider.createAccessToken(newUser.getEmail(), authentication);
            String refreshToken = tokenProvider.createRefreshToken(newUser.getEmail());

            newUser.setAccessToken(accessToken);
            newUser.setRefreshToken(refreshToken);
            userRepository.save(newUser);

            return new LoginResponse(accessToken, refreshToken);
        }
    }
}
