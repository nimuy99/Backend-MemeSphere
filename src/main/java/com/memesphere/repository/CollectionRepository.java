package com.memesphere.repository;

import com.memesphere.domain.Collection;
import com.memesphere.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Page<Collection> findAllByUser(User user, PageRequest pageRequest);
}
