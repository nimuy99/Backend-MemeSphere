package com.memesphere.service.user;

import com.memesphere.domain.User;
import com.memesphere.dto.user.response.KakaoTokenResponseDTO;
import com.memesphere.dto.user.response.KakaoUserInfoResponseDTO;
import com.memesphere.dto.user.response.UserResponseDTO;
import com.memesphere.jwt.TokenProvider;
import com.memesphere.repository.UserRepository;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

    private final TokenProvider tokenProvider;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

    private static final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";

    @Value("${security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    @Value("${security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    /*
    public KakaoTokenResponseDTO getAccessTokenFromKakao(String code) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String uri = UriComponentsBuilder.fromUriString(tokenUri)
                    .queryParam("grant_type", "authorization_code")
                    .queryParam("client_id", clientId)
                    .queryParam("code", code)
                    .toUriString();

            ResponseEntity<KakaoTokenResponseDTO> responseEntity = restTemplate.postForEntity(uri, null, KakaoTokenResponseDTO.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("Error occurred while getting access token from Kakao: ", e);
            throw new RuntimeException("Failed to retrieve access token from Kakao", e);
        }
    }
    */

    public String getAccessTokenFromKakao(String code) {

        KakaoTokenResponseDTO kakaoTokenResponseDTO = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("code", code)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("redirect_uri", "http://localhost:8080/user/login/oauth2/kakao")
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResponseDTO.class)
                .block();

        return kakaoTokenResponseDTO.getAccessToken();
    }

    public KakaoUserInfoResponseDTO getUserInfo(String accessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String uri = UriComponentsBuilder.fromUriString(userInfoUri)
                    .toUriString();

            ResponseEntity<KakaoUserInfoResponseDTO> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoUserInfoResponseDTO.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("Error occurred while getting user info from Kakao: ", e);
            throw new RuntimeException("Failed to retrieve user info from Kakao", e);
        }
    }

    // 사용자 정보로 회원가입 처리
    public void handleUserRegistration(KakaoUserInfoResponseDTO userInfo, KakaoTokenResponseDTO kakaoTokenResponseDTO) {

        Long kakaoId = userInfo.getId();
        User existingUser = userServiceImpl.findByKakaoId(kakaoId);

        if (existingUser == null) { // 유저가 존재하지 않으면 회원가입 처리
            User newUser = User.builder()
                    .kakaoId(kakaoId)
                    .nickname(userInfo.getKakaoAccount().getProfile().getNickName())
                    .email(userInfo.getKakaoAccount().getEmail())
                    .accessToken(kakaoTokenResponseDTO.getAccessToken())
                    .refreshToken(kakaoTokenResponseDTO.getRefreshToken())
                    .build();

            userServiceImpl.save(newUser);
        } else {
            // 이미 존재하는 경우 토큰을 업데이트
            User user = existingUser;
            user.setAccessToken(kakaoTokenResponseDTO.getAccessToken());
            user.setRefreshToken(kakaoTokenResponseDTO.getRefreshToken());
            userServiceImpl.save(user); // 갱신된 정보 저장
        }
    }

    public UserResponseDTO.LoginResult handleUserLogin(KakaoUserInfoResponseDTO userInfo) {
        User existingUser = userServiceImpl.findByKakaoId(userInfo.getId());

        String accessToken;
        if (existingUser != null) {
            // 이미 유저가 존재하면 Authentication 객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(existingUser.getEmail(), null, new ArrayList<>());

            // 액세스 토큰과 리프레쉬 토큰을 발급
            accessToken = tokenProvider.createAccessToken(existingUser.getEmail(), authentication);
            String refreshToken = tokenProvider.createRefreshToken(existingUser.getEmail());

            // 발급된 토큰을 기존 유저 객체에 업데이트
            existingUser.setAccessToken(accessToken);
            existingUser.setRefreshToken(refreshToken);

            // DB에 업데이트
            userRepository.save(existingUser);

            return new UserResponseDTO.LoginResult(accessToken, refreshToken);

        } else {
            // 유저가 없으면 회원가입 처리 후 토큰 발급
            User newUser = User.builder()
                    .kakaoId(userInfo.getId())
                    .nickname(userInfo.getKakaoAccount().getProfile().getNickName())
                    .email(userInfo.getKakaoAccount().getEmail())
                    .build(); // 회원가입 처리 로직 추가

            newUser = userRepository.save(newUser);

            Authentication authentication = new UsernamePasswordAuthenticationToken(existingUser.getEmail(), null, new ArrayList<>());

            accessToken = tokenProvider.createAccessToken(existingUser.getEmail(), authentication);
            String refreshToken = tokenProvider.createRefreshToken(existingUser.getEmail());

            newUser.setAccessToken(accessToken);
            newUser.setRefreshToken(refreshToken);

            userRepository.save(newUser);

            return new UserResponseDTO.LoginResult(accessToken, refreshToken);
        }
    }
}
