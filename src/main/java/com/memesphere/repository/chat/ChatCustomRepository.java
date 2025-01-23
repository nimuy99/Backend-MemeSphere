package com.memesphere.repository.chat;

import com.memesphere.domain.Chat;

public interface ChatCustomRepository {

    //최신 댓글 조회
    Chat findLatestMessageByCoinId(Long coin_id);
}
