package com.memesphere.service;

import com.memesphere.apipayload.code.status.ErrorStatus;
import com.memesphere.apipayload.exception.GeneralException;
import com.memesphere.converter.ChatConverter;
import com.memesphere.domain.Chat;
import com.memesphere.domain.MemeCoin;
import com.memesphere.domain.User;
import com.memesphere.dto.request.ChatRequest;
import com.memesphere.dto.response.ChatResponse;
import com.memesphere.repository.MemeRepository;
import com.memesphere.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MemeRepository memeRepository;
    private final ChatRepository chatRepository;

    public ChatResponse saveMessage(Long coin_id, ChatRequest chatRequest) {

        MemeCoin memeCoin = memeRepository.findById(coin_id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        Chat chat = ChatConverter.toChat(memeCoin, chatRequest);
        Chat savedChat = chatRepository.save(chat);

        return ChatConverter.toChatResponse(savedChat);
    }
}
