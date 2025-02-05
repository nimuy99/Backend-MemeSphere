package com.memesphere.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserInfoResponse {

    @Schema(description = "계정 유저 이름", example = "홍길동")
    private String name;

    @Schema(description = "계정 프로필 이미지", example = "https://lh3.googleusercontent.com/a/ACg8ocL1gRaTq2dfArGVEQC_fcEMdc101SbmOGE2u_-68LosJmIPOg=s96-c")
    private String picture;

    @Schema(description = "계정 이메일", example = "abc123@gmail.com")
    private String email;
}
