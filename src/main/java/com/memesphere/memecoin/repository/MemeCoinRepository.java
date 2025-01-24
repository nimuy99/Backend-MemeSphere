package com.memesphere.memecoin.repository;

import com.memesphere.memecoin.domain.MemeCoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemeCoinRepository extends JpaRepository<MemeCoin, Long> {
}
