package com.memesphere.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponse {

    @JsonProperty("id")
    public Long id;

    @JsonProperty("kakao_account")
    public UserInfoResponse kakaoUserInfo;
}
