package com.memesphere.chat.repository;

import com.memesphere.chat.domain.Chat;
import com.memesphere.memecoin.domain.MemeCoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long>, ChatCustomRepository {

    Chat findLatestMessageByMemeCoin(MemeCoin memeCoin);
}
