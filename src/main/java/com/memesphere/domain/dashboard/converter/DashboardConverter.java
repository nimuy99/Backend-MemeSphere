package com.memesphere.domain.dashboard.converter;

import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.domain.dashboard.dto.response.DashboardOverviewResponse;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.dashboard.dto.response.DashboardTrendListResponse;
import com.memesphere.domain.dashboard.dto.response.DashboardTrendResponse;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DashboardConverter {
    // ** 총 거래량 및 총 개수 응답 형식 ** //
    public static DashboardOverviewResponse toOverView(BigDecimal totalVolume, Long totalCoin) {
        return DashboardOverviewResponse.builder()
                .totalVolume(totalVolume)
                .totalCoin(totalCoin)
                .build();
    }

    // ** 트렌드 응답 형식 ** //
    public static DashboardTrendListResponse toTrendList(LocalDateTime recordedTime,
                                                         List<ChartData> dataList,
                                                         List<MemeCoin> prevCoinList) {

        List<DashboardTrendResponse> trendList = new ArrayList<>();

        // 현재 top5에 들지 않는 밈코인 순위 처리
        List<MemeCoin> currCoinList = dataList.stream().map(ChartData::getMemeCoin).toList();
        for (MemeCoin prevCoin : prevCoinList) {
            if (!currCoinList.contains(prevCoin)) prevCoin.updateRank(null);
        }


        // 현재 top5 밈코인
        for (int i = 0; i < 5; i++) {
            ChartData data = dataList.get(i);
            DashboardTrendResponse trend = toTrend(data, prevCoinList, i + 1);
            trendList.add(trend);
        }

        return DashboardTrendListResponse.builder()
                .timestamp(recordedTime)
                .trendList(trendList)
                .build();
    }

    public static DashboardTrendResponse toTrend(ChartData data,
                                                 List<MemeCoin> prevCoinList,
                                                 Integer currRank) {
        MemeCoin memeCoin = data.getMemeCoin();

        // 이전에 기록된 밈코인 순위와 현재 밈코인 순위 변화
        // prevRank == -1 : 새로 등록 순위
        Integer prevRank = prevCoinList.indexOf(memeCoin);
        prevRank = (prevRank == -1) ? null : prevRank + 1;
        String rankDirection = (prevRank == null || currRank <= prevRank) ? "up" : "down";

        // 순위 업데이트
        memeCoin.updateRank(currRank);

        return DashboardTrendResponse.builder()
                .coinId(memeCoin.getId())
                .image(memeCoin.getImage())
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .volume(data.getVolume())
                .price(data.getPrice())
                .priceChange(data.getPriceChange())
                .priceChangeAbsolute(data.getPriceChange().abs())
                .priceChangeDirection(data.getPriceChangeRate().compareTo(BigDecimal.ZERO) < 0 ? "down" : "up")
                .priceChangeRate(data.getPriceChangeRate())
                .rankChangeDirection(rankDirection)
                .build();
    }
}