package com.memesphere.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;

@NoArgsConstructor // 역직렬화를 위한 기본 생성자
@AllArgsConstructor
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

    @JsonProperty("token_type")
    @Schema(description = "토큰 타입", example = "Bearer")
    private String tokenType;

    @JsonProperty("access_token")
    @Schema(description = "액세스 토큰", example = "1234567890abcdef")
    private String accessToken;

    @JsonProperty("id_token")
    @Schema(description = "ID 토큰", example = "abcdef123456")
    private String idToken;

    @JsonProperty("expires_in")
    @Schema(description = "토큰 만료 시간(초)", example = "3600")
    private Integer expiresIn;

    @JsonProperty("refresh_token")
    @Schema(description = "리프레시 토큰", example = "abcdef1234567890")
    private String refreshToken;

    @JsonProperty("refresh_token_expires_in")
    @Schema(description = "리프레시 토큰 만료 시간(초)", example = "7200")
    private Integer refreshTokenExpiresIn;

    @JsonProperty("scope")
    @Schema(description = "허가된 범위", example = "profile, account")
    private String scope;
}


