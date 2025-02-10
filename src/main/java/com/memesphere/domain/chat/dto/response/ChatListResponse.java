package com.memesphere.domain.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChatListResponse {

    @Schema(description = "채팅 리스트")
    private List<ChatResponse> chats;
}
