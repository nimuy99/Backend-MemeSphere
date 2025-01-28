package com.memesphere.memecoin.service;

import com.memesphere.chartdata.domain.ChartData;

public interface MemeCoinQueryService {
    void updateChartData(Long memeCoinId, ChartData newChartData);
}
