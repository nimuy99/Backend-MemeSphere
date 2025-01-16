package com.memesphere.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ChatResponse {

    @Schema(description = "채팅 아이디(번호)", example = "1")
    private Long id;

    @Schema(description = "메시지 내용", example = "코인이 상승할 것 같습니다.")
    private String message;

    @Schema(description = "관련 밈코인", example = "도지코인")
    private String memeCoin;

//    @Schema(description = "작성자 닉네임")
//    private String nickname;

    @Schema(description = "좋아요 수", example = "17")
    private int likes;

    @Schema(description = "전송 시간", example = "2025-01-01T00:00:00")
    private LocalDateTime createdAt;
}
