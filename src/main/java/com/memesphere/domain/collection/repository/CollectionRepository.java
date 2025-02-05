package com.memesphere.domain.collection.repository;

import com.memesphere.domain.collection.entity.Collection;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Page<Collection> findAllByUserId(Long userId, Pageable pageable);
    List<Collection> findAllByUserId(Long userId);
    Boolean existsByUserAndMemeCoin(User user, MemeCoin memeCoin);
}
