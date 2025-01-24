package com.memesphere.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthUserInfoResponse {

    @JsonProperty("id")
    public Long id;

    @JsonProperty("auth_account")
    public UserInfoResponse authUserInfo;
}
