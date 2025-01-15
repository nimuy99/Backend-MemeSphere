package com.memesphere.repository;

import com.memesphere.domain.ChartData;
import com.memesphere.domain.MemeCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChartDataRepository extends JpaRepository<ChartData, Long> {
    @Query("SELECT c FROM ChartData c WHERE c.memeCoin = :coin ORDER BY c.createdAt DESC")
    ChartData findLatestDataByCoin(MemeCoin coin);
}
