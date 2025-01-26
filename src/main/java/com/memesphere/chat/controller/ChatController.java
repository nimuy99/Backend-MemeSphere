package com.memesphere.chat.controller;

import com.memesphere.chat.dto.request.ChatRequest;
import com.memesphere.chat.dto.response.ChatResponse;
import com.memesphere.chat.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "실시간 채팅", description = "실시간 채팅 관련 API")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // TODO: @AuthenticationPrincipal로 사용자 이름 받아오기
    @MessageMapping("/chat/{coin_id}")
    @SendTo("/sub/{coin_id}")
    public ChatResponse chat(@DestinationVariable("coin_id") Long coin_id,
                                     @Payload ChatRequest chatRequest) {

        return chatService.saveMessage(coin_id, chatRequest);
    }
}
