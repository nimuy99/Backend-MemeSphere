package com.memesphere.domain.user.repository;

import com.memesphere.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByLoginId(Long loginId);
    Optional<User> findByAccessToken(String accessToken);
}
