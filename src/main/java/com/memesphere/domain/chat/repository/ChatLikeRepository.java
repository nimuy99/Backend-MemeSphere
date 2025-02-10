package com.memesphere.domain.chat.repository;

import com.memesphere.domain.chat.entity.Chat;
import com.memesphere.domain.chat.entity.ChatLike;
import com.memesphere.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatLikeRepository extends JpaRepository<ChatLike, Long> {

    Optional<ChatLike> findByChatAndUser(Chat chat, User user);
}
