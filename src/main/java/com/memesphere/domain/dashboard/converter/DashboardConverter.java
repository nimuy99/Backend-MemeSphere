package com.memesphere.domain.dashboard.converter;

import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.domain.dashboard.dto.response.DashboardOverviewResponse;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.dashboard.dto.response.DashboardTrendListResponse;
import com.memesphere.domain.dashboard.dto.response.DashboardTrendResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardConverter {
    // ** 총 거래량 및 총 개수 응답 형식 ** //
    public static DashboardOverviewResponse toOverView(BigDecimal totalVolume, Long totalCoin) {
        return DashboardOverviewResponse.builder()
                .totalVolume(totalVolume)
                .totalCoin(totalCoin)
                .build();
    }

    // ** 트렌드 응답 형식 ** //
    public static DashboardTrendListResponse toTrendList(LocalDateTime recordedTime, List<ChartData> dataList) {
        List<DashboardTrendResponse> trendList = dataList.stream()
                .map(data -> toTrend(data))
                .collect(Collectors.toList());

        return DashboardTrendListResponse.builder()
                .timestamp(recordedTime)
                .trendList(trendList)
                .build();
    }

    public static DashboardTrendResponse toTrend(ChartData data) {
        MemeCoin memeCoin = data.getMemeCoin();

        return DashboardTrendResponse.builder()
                .coinId(memeCoin.getId())
                .image(memeCoin.getImage())
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .volume(data.getVolume())
                .price(data.getPrice())
                .priceChange(data.getPriceChange())
                .changeAbsolute(data.getPriceChange().abs())
                .changeDirection(data.getPriceChangeRate().compareTo(BigDecimal.ZERO) < 0 ? "down" : "up")
                .changeRate(data.getPriceChangeRate())
                .build();
    }
}