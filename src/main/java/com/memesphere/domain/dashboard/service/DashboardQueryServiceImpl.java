package com.memesphere.domain.dashboard.service;

import com.memesphere.domain.dashboard.converter.DashboardConverter;
import com.memesphere.domain.chartdata.domain.ChartData;
import com.memesphere.domain.dashboard.dto.response.DashboardOverviewResponse;
import com.memesphere.domain.dashboard.dto.response.DashboardTrendListResponse;
import com.memesphere.domain.chartdata.repository.ChartDataRepository;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashboardQueryServiceImpl implements DashboardQueryService {
    private final MemeCoinRepository coinRepository;
    private final ChartDataRepository chartDataRepository;

    // ** 총 거래량 및 총 개수 ** //
    @Override
    public DashboardOverviewResponse getOverview() {
        // 등록된 모든 밈코인의 총 거래량
        Long totalVolume = chartDataRepository.findTotalVolume();

        // 등록된 모든 밈코인 수
        Long totalCoin = coinRepository.count();

        return DashboardConverter.toOverView(totalVolume, totalCoin);
    }

    // ** 트렌드 ** //
    @Override
    public DashboardTrendListResponse getTrendList() {
        // 거래량 top5 밈코인-차트데이터
        List<ChartData> dataList = chartDataRepository.findTop5ByOrderByVolumeDesc();

        return DashboardConverter.toTrendList(dataList);
    }

    // ** 차트 ** //

}
