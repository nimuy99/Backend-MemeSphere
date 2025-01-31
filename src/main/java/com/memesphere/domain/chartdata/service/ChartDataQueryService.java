package com.memesphere.domain.chartdata.service;

import com.memesphere.domain.chartdata.domain.ChartData;
import com.memesphere.domain.memecoin.domain.MemeCoin;

public interface ChartDataQueryService {
    void saveChartData(MemeCoin memeCoin, ChartData chartData);
}
