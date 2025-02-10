package com.memesphere.domain.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

    @Schema(description = "닉네임", example = "코인전문가")
    private String nickname;

    @Schema(description = "메시지 내용", example = "도지코인 현재 얼마인가요?")
    private String message;
}
