package com.memesphere.repository;

import com.memesphere.domain.MemeCoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemeRepository extends JpaRepository<MemeCoin, Long> {
}
