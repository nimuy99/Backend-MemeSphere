package com.memesphere.chartdata.service;

import com.memesphere.chartdata.domain.ChartData;
import com.memesphere.memecoin.domain.MemeCoin;

public interface ChartDataQueryService {
    void saveChartData(MemeCoin memeCoin, ChartData chartData);
}
