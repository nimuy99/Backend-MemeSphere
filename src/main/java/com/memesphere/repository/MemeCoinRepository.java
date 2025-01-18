package com.memesphere.repository;

import com.memesphere.domain.MemeCoin;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemeCoinRepository extends JpaRepository<MemeCoin, Long> {
}
