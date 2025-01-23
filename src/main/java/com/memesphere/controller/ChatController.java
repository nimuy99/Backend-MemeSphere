package com.memesphere.controller;

import com.memesphere.apipayload.ApiResponse;
import com.memesphere.domain.Chat;
import com.memesphere.dto.request.ChatRequest;
import com.memesphere.dto.response.ChatResponse;
import com.memesphere.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    //최신 댓글 조회 Api
    @GetMapping("/latest/{coin_id}")
    @Operation(summary = "코인별 최신 댓글 조회 API",
            description = "특정 코인에 대한 최신 댓글을 반환합니다. 요청 시 최신 댓글 하나만 가져옵니다.")
    public ApiResponse<ChatResponse> getLatestMessages(
            @PathVariable("coin_id") Long coin_id) {

        // 최신 댓글을 가져오는 서비스 메서드 호출
        ChatResponse latestMessage = chatService.getLatestMessages(coin_id);

        return ApiResponse.onSuccess(latestMessage);
    }


}
