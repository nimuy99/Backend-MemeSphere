package com.memesphere.domain.chartdata.repository;

import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChartDataRepository extends JpaRepository<ChartData, Long> {
    @Query("SELECT SUM(c.volume) FROM ChartData c")
    Long findTotalVolume();

    @EntityGraph(attributePaths = {"memeCoin"})
    List<ChartData> findTop5ByOrderByVolumeDesc();

    List<ChartData> findByMemeCoinOrderByRecordedTimeDesc(MemeCoin memeCoin);
}
