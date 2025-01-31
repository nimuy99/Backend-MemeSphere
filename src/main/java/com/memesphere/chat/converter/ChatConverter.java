package com.memesphere.chat.converter;

import com.memesphere.chat.domain.Chat;
import com.memesphere.domain.memecoin.domain.MemeCoin;
import com.memesphere.chat.dto.request.ChatRequest;
import com.memesphere.chat.dto.response.ChatResponse;

public class ChatConverter {

    public static Chat toChat(MemeCoin memeCoin, ChatRequest chatRequest) {

        return Chat.builder()
//                .user(user)
                .memeCoin(memeCoin)
                .message(chatRequest.getMessage())
                .build();
    }

    public static ChatResponse toChatResponse(Chat chat) {

        return ChatResponse.builder()
                .id(chat.getId())
                .message(chat.getMessage())
                .memeCoin(chat.getMemeCoin().getName())
                .likes(chat.getChatLikeList().size())
                .createdAt(chat.getCreatedAt())
                .build();
    }
}
