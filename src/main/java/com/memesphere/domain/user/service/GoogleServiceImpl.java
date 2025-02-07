package com.memesphere.domain.user.service;

import com.memesphere.domain.user.converter.UserConverter;
import com.memesphere.domain.user.dto.response.GoogleUserInfoResponse;
import com.memesphere.domain.user.dto.response.KakaoUserInfoResponse;
import com.memesphere.domain.user.dto.response.LoginResponse;
import com.memesphere.domain.user.dto.response.TokenResponse;
import com.memesphere.domain.user.entity.SocialType;
import com.memesphere.domain.user.entity.User;
import com.memesphere.domain.user.repository.UserRepository;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleServiceImpl implements GoogleService{

    private final TokenProvider tokenProvider;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

    @Value("${security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${security.oauth2.client.registration.google.authorization-grant-type}")
    private String authorizationCode;

    @Value("${security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;

    @Value("${security.oauth2.client.provider.google.user-info-uri}")
    private String userInfoUri;


    public TokenResponse getAccessTokenFromGoogle(String code) {
        try {
            String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

            RestTemplate restTemplate = new RestTemplate();
            String uri = UriComponentsBuilder.fromUriString(tokenUri)
                    .queryParam("grant_type", authorizationCode)
                    .queryParam("client_id", clientId)
                    .queryParam("redirect_uri", redirectUri)
                    .queryParam("code", decodedCode)
                    .queryParam("client_secret", clientSecret)
                    .toUriString();

            ResponseEntity<TokenResponse> responseEntity = restTemplate.postForEntity(uri, null, TokenResponse.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("Error occurred while getting access token from Google: ", e);
            throw new RuntimeException("Failed to retrieve access token from Google", e);
        }
    }

    public GoogleUserInfoResponse getUserInfo(String accessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String uri = UriComponentsBuilder.fromUriString(userInfoUri).toUriString();

            ResponseEntity<GoogleUserInfoResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, GoogleUserInfoResponse.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("Error occurred while getting user info from Google: ", e);
            throw new RuntimeException("Failed to retrieve user info from Google", e);
        }
    }

    public void handleUserRegistration(GoogleUserInfoResponse googleUserInfoResponse, TokenResponse tokenResponse) {
        String email = googleUserInfoResponse.getEmail();
        User existingUser = userRepository.findByEmail(email).orElse(null);

        if (existingUser == null) {
            User newUser = UserConverter.toGoogleUser(googleUserInfoResponse);
            userServiceImpl.save(newUser);
        } else {
            User updatedUser = UserConverter.toUpdatedGoogleUser(googleUserInfoResponse, tokenResponse);
            userServiceImpl.save(updatedUser);
        }
    }

    public LoginResponse handleUserLogin(GoogleUserInfoResponse googleUserInfoResponse) {
        User existingUser = userRepository.findByEmail(googleUserInfoResponse.getEmail()).orElse(null);
        String accessToken;

        if (existingUser != null) {

            accessToken = tokenProvider.createAccessToken(existingUser.getEmail(), existingUser.getLoginId());
            String refreshToken = tokenProvider.createRefreshToken(existingUser.getEmail());

            String nickname = existingUser.getNickname();

            existingUser.saveAccessToken(accessToken);
            existingUser.saveRefreshToken(refreshToken);
            userRepository.save(existingUser);

            return new LoginResponse(accessToken, refreshToken, nickname);
        } else {
            User newUser = UserConverter.toGoogleUser(googleUserInfoResponse);
            newUser = userRepository.save(newUser);

            accessToken = tokenProvider.createAccessToken(newUser.getEmail(), newUser.getLoginId());
            String refreshToken = tokenProvider.createRefreshToken(newUser.getEmail());

            String nickname = newUser.getNickname();

            newUser.saveAccessToken(accessToken);
            newUser.saveRefreshToken(refreshToken);
            userRepository.save(newUser);

            return new LoginResponse(accessToken, refreshToken, nickname);
        }
    }
}
