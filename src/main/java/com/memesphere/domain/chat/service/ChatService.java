package com.memesphere.domain.chat.service;

import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.domain.chat.converter.ChatConverter;
import com.memesphere.domain.chat.entity.Chat;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.chat.dto.request.ChatRequest;
import com.memesphere.domain.chat.dto.response.ChatResponse;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import com.memesphere.domain.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MemeCoinRepository memeCoinRepository;
    private final ChatRepository chatRepository;

    public ChatResponse saveMessage(Long coin_id, ChatRequest chatRequest) {

        MemeCoin memeCoin = memeCoinRepository.findById(coin_id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        Chat chat = ChatConverter.toChat(memeCoin, chatRequest);
        Chat savedChat = chatRepository.save(chat);

        return ChatConverter.toChatResponse(savedChat);
    }
}
