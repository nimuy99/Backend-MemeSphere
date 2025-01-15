package com.memesphere.repository.chat;

import com.memesphere.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long>, ChatCustomRepository {
}
