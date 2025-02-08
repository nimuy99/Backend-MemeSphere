package com.memesphere.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Schema(title = "회원가입 요청 DTO")
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotEmpty
    @Schema(description = "사용자 이메일", example = "memesphere@meme.com")
    private String email;

    @NotEmpty
    @Schema(description = "사용자 비밀번호", example = "meme123")
    private String password;

    @NotEmpty
    @Schema(description = "사용자 닉네임", example = "홍길동")
    String nickname;

    @NotEmpty
    @Schema(description = "사용자 생년월일", example = "20001010")
    String birth;

    @NotEmpty
    @Schema(description = "프로필 이미지", example = "http://umc..jpg")
    String profileImage;
}
