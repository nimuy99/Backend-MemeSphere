package com.memesphere.chartdata.repository;

import com.memesphere.chartdata.domain.ChartData;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChartDataRepository extends JpaRepository<ChartData, Long> {
    @Query("SELECT SUM(c.volume) FROM ChartData c")
    Long findTotalVolume();

    @EntityGraph(attributePaths = {"memeCoin"})
    List<ChartData> findTop5ByOrderByVolumeDesc();
}
