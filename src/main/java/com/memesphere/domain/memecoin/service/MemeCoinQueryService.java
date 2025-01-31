package com.memesphere.domain.memecoin.service;

import com.memesphere.domain.chartdata.domain.ChartData;

public interface MemeCoinQueryService {
    void updateChartData(Long memeCoinId, ChartData newChartData);
}
