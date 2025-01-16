package com.memesphere.converter;

import com.memesphere.domain.Chat;
import com.memesphere.domain.MemeCoin;
import com.memesphere.domain.User;
import com.memesphere.dto.request.ChatRequest;
import com.memesphere.dto.response.ChatResponse;

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
