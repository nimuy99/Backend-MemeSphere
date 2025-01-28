package com.memesphere.memecoin.repository;

import com.memesphere.memecoin.domain.MemeCoin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemeRepository extends JpaRepository<MemeCoin, Long> {
    @EntityGraph(attributePaths = {"chartData"})
    Page<MemeCoin> findByNameContainingIgnoreCaseOrSymbolContainingIgnoreCaseOrKeywordsContainingIgnoreCase(String name, String symbol, String keyword, Pageable pageable);

    Optional<MemeCoin> findByName(String name);
}
