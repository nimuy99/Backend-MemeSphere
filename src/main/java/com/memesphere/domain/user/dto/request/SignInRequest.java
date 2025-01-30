package com.memesphere.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {

    @NotEmpty
    @Schema(description = "이메일", example = "memesphere@meme.com")
    private String email;

    @NotEmpty
    @Schema(description = "비밀번호", example = "meme123")
    private String password;
}
