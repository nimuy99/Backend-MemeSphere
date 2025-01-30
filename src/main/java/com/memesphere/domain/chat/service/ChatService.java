package com.memesphere.domain.chat.service;

import com.memesphere.domain.chat.converter.ChatConverter;
import com.memesphere.domain.chat.entity.Chat;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.chat.dto.request.ChatRequest;
import com.memesphere.domain.chat.dto.response.ChatResponse;
import com.memesphere.domain.chat.repository.ChatRepository;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
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

    // 최신 댓글을 가져오는 메서드
    public ChatResponse getLatestMessages(Long coin_id) {


        MemeCoin memeCoin = memeCoinRepository.findById(coin_id)
                //id에 해당하는 밈코인 없을 때
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        // 최신 댓글을 하나만 가져옴
        Chat latestChat = chatRepository.findFirstByMemeCoin_IdOrderByCreatedAtDesc(coin_id);

        // 댓글이 없으면 null 반환
        if (latestChat == null) {
            return null;
        }

        // 최신 댓글을 ChatResponse로 변환하여 반환
        return ChatConverter.toChatResponse(latestChat);
    }

}
