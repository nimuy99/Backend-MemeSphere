package com.memesphere.chat.repository;

import com.memesphere.chat.domain.Chat;

public interface ChatCustomRepository {

    //최신 댓글 조회
    Chat findLatestMessageByCoinId(Long coin_id);
}
