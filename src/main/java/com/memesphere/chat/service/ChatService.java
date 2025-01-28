package com.memesphere.chat.service;

import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.chat.converter.ChatConverter;
import com.memesphere.chat.domain.Chat;
import com.memesphere.memecoin.domain.MemeCoin;
import com.memesphere.chat.dto.request.ChatRequest;
import com.memesphere.chat.dto.response.ChatResponse;
import com.memesphere.memecoin.repository.MemeCoinRepository;
import com.memesphere.chat.repository.ChatRepository;
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
