package com.memesphere.domain.memecoin.service;

import com.memesphere.domain.chartdata.entity.ChartData;

public interface MemeCoinQueryService {
    void updateChartData(Long memeCoinId, ChartData newChartData, Long userId);
}
