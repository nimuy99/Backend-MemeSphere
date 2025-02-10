package com.memesphere.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Schema(title = "액세스 토큰 재발급 요청 DTO")
@AllArgsConstructor
@NoArgsConstructor
public class ReissueRequest {

    @NotEmpty
    @Schema(description = "리프레시 토큰", example = "dklksdfsdklkfds8326v5cf5d5d6s6flk9876542316468645")
    private String refreshToken;
}
