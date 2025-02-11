package com.memesphere.domain.chat.service;

import com.memesphere.domain.chat.converter.ChatConverter;
import com.memesphere.domain.chat.entity.Chat;
import com.memesphere.domain.chat.entity.ChatLike;
import com.memesphere.domain.chat.repository.ChatLikeRepository;
import com.memesphere.domain.user.entity.User;
import com.memesphere.domain.user.repository.UserRepository;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.chat.dto.request.ChatRequest;
import com.memesphere.domain.chat.dto.response.ChatResponse;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import com.memesphere.domain.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MemeCoinRepository memeCoinRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatLikeRepository chatLikeRepository;

    public ChatResponse saveMessage(Long coin_id, ChatRequest chatRequest) {

        MemeCoin memeCoin = memeCoinRepository.findById(coin_id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        User user = userRepository.findByNickname(chatRequest.getNickname())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Chat chat = ChatConverter.toChat(memeCoin, chatRequest, user);
        Chat savedChat = chatRepository.save(chat);

        return ChatConverter.toChatResponse(savedChat);
    }

    @Transactional
    public Page<ChatResponse> getChatList(Long coin_id, Pageable pageable) {

        Page<Chat> chatPage = chatRepository.findAllByMemeCoin_Id(coin_id, pageable);
        return chatPage.map(ChatConverter::toChatResponse);
    }

    // 최신 댓글을 가져오는 메서드
    @Transactional
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

    @Transactional
    public String postChatLike(Long chat_id, Long user_id) {

        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Chat chat = chatRepository.findById(chat_id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHAT_NOT_FOUND));

        // 사용자가 좋아요 눌렀는지 확인
        Optional<ChatLike> existingLike = chatLikeRepository.findByChatAndUser(chat, user);
        System.out.println("좋아요" + existingLike.isPresent());

        if (existingLike.isPresent()) {
            chatLikeRepository.delete(existingLike.get());
            return "좋아요를 취소했습니다.";
        } else {
            ChatLike chatLike = ChatConverter.toChatLike(chat, user);
            chatLikeRepository.save(chatLike);
            return "좋아요를 눌렀습니다.";
        }
    }

}
