package com.memesphere.repository;

import com.memesphere.domain.MemeCoin;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemeCoinRepository extends JpaRepository<MemeCoin, Long> {
    @Query("SELECT SUM(m.volume) FROM MemeCoin m")
    Long findTotalVolume();

    @Query("SELECT m FROM MemeCoin m ORDER BY m.volume DESC")
    List<MemeCoin> findTrendTop5(Pageable pageable);
}
