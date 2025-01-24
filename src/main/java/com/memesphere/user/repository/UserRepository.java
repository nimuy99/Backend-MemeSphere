package com.memesphere.user.repository;

import com.memesphere.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByLoginId(Long loginId);
    Optional<User> findByAccessToken(String accessToken);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);
}
