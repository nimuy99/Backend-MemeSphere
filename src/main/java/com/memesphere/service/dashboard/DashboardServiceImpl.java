package com.memesphere.service.dashboard;

import com.memesphere.converter.DashboardConverter;
import com.memesphere.domain.ChartData;
import com.memesphere.domain.MemeCoin;
import com.memesphere.dto.DashboardDTO;
import com.memesphere.repository.ChartDataRepository;
import com.memesphere.repository.MemeCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
    private final MemeCoinRepository coinRepository;
    private final ChartDataRepository chartDataRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardDTO.TrendResponse findTrendList() {
        // 등록된 모든 밈코인의 총 거래량
        Long totalVolume = coinRepository.findTotalVolume();

        // 등록된 모든 밈코인 수
        Long totalCoin = coinRepository.count();

        // 거래량 top5 밈코인
        Pageable pageable = PageRequest.of(0, 5);
        List<MemeCoin> trendCoinList = coinRepository.findTrendTop5(pageable);

        // top5 밈코인의 최신 차트데이터
        List<ChartData> chartDataList = trendCoinList.stream()
                .map(chartDataRepository::findLatestDataByCoin)
                .toList();

        return DashboardConverter.toTrendResponse(totalVolume, totalCoin, trendCoinList, chartDataList);
    }

    @Override
    public DashboardDTO.RelatedSearchResponse findRelatedSearch() {
        return null;
    }
}
