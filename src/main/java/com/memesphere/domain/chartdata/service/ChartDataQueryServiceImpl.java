package com.memesphere.domain.chartdata.service;

import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.domain.chartdata.repository.ChartDataRepository;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartDataQueryServiceImpl implements ChartDataQueryService {
    private final ChartDataRepository chartDataRepository;
    private static final int MAX_CHART_DATA_CNT = 6;

    @Override
    @Transactional
    public void saveChartData(MemeCoin memeCoin, ChartData newChartData) {
        List<ChartData> chartDataList = chartDataRepository.findByMemeCoinOrderByRecordedTimeDesc(memeCoin);

        if (chartDataList.size() >= MAX_CHART_DATA_CNT) {
            ChartData oldestData = chartDataList.get(chartDataList.size() - 1);
            chartDataRepository.delete(oldestData);
            chartDataRepository.flush();
        }

        newChartData.setMemeCoin(memeCoin);
        chartDataRepository.save(newChartData);
    }
}
