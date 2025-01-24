package com.memesphere.user.service;

import com.memesphere.user.converter.UserConverter;
import com.memesphere.user.domain.User;
import com.memesphere.user.domain.SocialType;
import com.memesphere.user.dto.response.LoginResponse;
import com.memesphere.user.dto.response.KakaoUserInfoResponse;
import com.memesphere.global.jwt.TokenProvider;
import com.memesphere.user.dto.response.TokenResponse;
import com.memesphere.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    public TokenResponse getAccessTokenFromKakao(String code) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String uri = UriComponentsBuilder.fromUriString(tokenUri)
                    .queryParam("grant_type", "authorization_code")
                    .queryParam("client_id", clientId)
                    .queryParam("code", code)
                    .toUriString();

            ResponseEntity<TokenResponse> responseEntity = restTemplate.postForEntity(uri, null, TokenResponse.class);
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

    public void handleUserRegistration(KakaoUserInfoResponse kakaoUserInfoResponse, TokenResponse tokenResponse) {
        Long loginId = kakaoUserInfoResponse.getId();
        User existingUser = userServiceImpl.findByLoginId(loginId);

        if (existingUser == null) { // 유저가 존재하지 않으면 회원가입 처리
            User newUser = UserConverter.toKakaoUser(kakaoUserInfoResponse); // 신규 유저 생성
            userServiceImpl.save(newUser);
        } else {
            User updatedUser = UserConverter.toUpdatedKakaoUser(kakaoUserInfoResponse, tokenResponse); // 기존 유저 업데이트
            existingUser.setSocialType(SocialType.KAKAO);
            userServiceImpl.save(updatedUser);
        }
    }

    public LoginResponse handleUserLogin(KakaoUserInfoResponse kakaoUserInfoResponse) {
        User existingUser = userServiceImpl.findByLoginId(kakaoUserInfoResponse.getId());
        String accessToken;

        if (existingUser != null) {

            accessToken = tokenProvider.createAccessToken(existingUser.getEmail(), existingUser.getLoginId());
            String refreshToken = tokenProvider.createRefreshToken(existingUser.getEmail());

            existingUser.setAccessToken(accessToken);
            existingUser.setRefreshToken(refreshToken);
            userRepository.save(existingUser);

            return new LoginResponse(accessToken, refreshToken);
        } else {
            User newUser = UserConverter.toKakaoUser(kakaoUserInfoResponse);
            newUser = userRepository.save(newUser);

            accessToken = tokenProvider.createAccessToken(newUser.getEmail(), newUser.getLoginId());
            String refreshToken = tokenProvider.createRefreshToken(newUser.getEmail());

            newUser.setAccessToken(accessToken);
            newUser.setRefreshToken(refreshToken);
            userRepository.save(newUser);

            return new LoginResponse(accessToken, refreshToken);
        }
    }
}
