package com.memesphere.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponse {

    @Schema(description = "계정 아이디", example = "1")
    @JsonProperty("id")
    public Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {
        @Schema(description = "계정 아이디", example = "1")
        private Long loginId;

        @Schema(description = "계정 이메일", example = "abc123@gmail.com")
        @JsonProperty("email")
        public String email;

        @JsonProperty("profile")
        public Profile profile;

        @Getter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Profile {

            @Schema(description = "계정 닉네임", example = "홍길동")
            @JsonProperty("nickname")
            public String nickName;

            @Schema(description = "계정 프로필 이미지", example = "프로필 사진 URL")
            @JsonProperty("profile_image_url")
            public String profileImageUrl;
        }
    }
}
