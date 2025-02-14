package com.memesphere.domain.chartdata.service;

import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.domain.memecoin.entity.MemeCoin;

public interface ChartDataQueryService {
    void saveChartData(MemeCoin memeCoin, ChartData chartData, Long userId);
}
