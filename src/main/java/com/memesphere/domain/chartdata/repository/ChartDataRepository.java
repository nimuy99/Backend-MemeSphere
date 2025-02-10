package com.memesphere.domain.chartdata.repository;

import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ChartDataRepository extends JpaRepository<ChartData, Long> {
    @Query("SELECT SUM(c.volume) FROM ChartData c " +
            "WHERE c.recordedTime = " +
            "(SELECT MAX(c2.recordedTime) FROM ChartData c2 WHERE c2.memeCoin = c.memeCoin)")
    BigDecimal findTotalVolume();

    @Query("SELECT c FROM ChartData c " +
            "WHERE c.recordedTime = " +
            "(SELECT MAX(c2.recordedTime) FROM ChartData c2 WHERE c2.memeCoin = c.memeCoin) " +
            "ORDER BY c.volume DESC LIMIT 5")
    List<ChartData> findTop5OrderByVolumeDesc();

    @Query("SELECT MAX(c.recordedTime) FROM ChartData c WHERE c.memeCoin.id = 1")
    LocalDateTime findRecordedTimeByCoinId1();

    List<ChartData> findByMemeCoinOrderByRecordedTimeDesc(MemeCoin memeCoin);
}
