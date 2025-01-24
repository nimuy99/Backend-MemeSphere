package com.memesphere.chat.service;

import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.chat.converter.ChatConverter;
import com.memesphere.chat.domain.Chat;
import com.memesphere.memecoin.domain.MemeCoin;
import com.memesphere.chat.dto.request.ChatRequest;
import com.memesphere.chat.dto.response.ChatResponse;
import com.memesphere.memecoin.repository.MemeRepository;
import com.memesphere.chat.repository.ChatRepository;
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

    // 최신 댓글을 가져오는 메서드
    public ChatResponse getLatestMessages(Long coin_id) {

        //id에 해당하는 밈코인 없을 때
        MemeCoin memeCoin = memeRepository.findById(coin_id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        // 최신 댓글을 하나만 가져옴
        Chat latestChat = chatRepository.findLatestMessageByCoinId(coin_id);

        // 댓글이 없으면 null 반환 - 필요없나? (null 값 처리)
        if (latestChat == null) {
            return null;
        }

        // 최신 댓글을 ChatResponse로 변환하여 반환
        return ChatConverter.toChatResponse(latestChat);
    }

}
