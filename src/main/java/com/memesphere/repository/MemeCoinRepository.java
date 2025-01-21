package com.memesphere.repository;

import com.memesphere.domain.MemeCoin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemeCoinRepository extends JpaRepository<MemeCoin, Long> {
    Page<MemeCoin> findByNameContainingIgnoreCaseOrSymbolContainingIgnoreCaseOrKeywordsContainingIgnoreCase(String name, String symbol, String keyword, Pageable pageable);
}
