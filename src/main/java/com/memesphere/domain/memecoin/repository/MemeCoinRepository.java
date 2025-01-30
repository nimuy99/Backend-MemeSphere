package com.memesphere.domain.memecoin.repository;

import com.memesphere.domain.memecoin.entity.MemeCoin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface MemeCoinRepository extends JpaRepository<MemeCoin, Long> {
    @EntityGraph(attributePaths = {"chartData"})
    Page<MemeCoin> findByNameContainingIgnoreCaseOrSymbolContainingIgnoreCaseOrKeywordsContainingIgnoreCase(String name, String symbol, String keyword, Pageable pageable);

    @EntityGraph(attributePaths = {"chartData"})
    @NonNull
    Page<MemeCoin> findAll(@NonNull Pageable pageable);
}
