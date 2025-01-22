package com.memesphere.converter;

import com.memesphere.domain.ChartData;
import com.memesphere.domain.MemeCoin;
import com.memesphere.dto.response.DashboardOverviewResponse;
import com.memesphere.dto.response.DashboardTrendListResponse;
import com.memesphere.dto.response.DashboardTrendResponse;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardConverter {
    // ** 총 거래량 및 총 개수 응답 형식 ** //
    public static DashboardOverviewResponse toOverView(Long totalVolume, Long totalCoin) {
        return DashboardOverviewResponse.builder()
                .totalVolume(totalVolume)
                .totalCoin(totalCoin)
                .build();
    }

    // ** 트렌드 응답 형식 ** //
    public static DashboardTrendListResponse toTrendList(List<ChartData> dataList) {
        List<DashboardTrendResponse> trendList = dataList.stream()
                .map(data -> toTrend(data))
                .collect(Collectors.toList());

        return DashboardTrendListResponse.builder()
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
                .price(data.getPrice().intValue()) // todo: 외부 api 응답 형식 보고 엔티티 자료형 변경
                .priceChange(data.getPrice_change().intValue())
                .changeAbsolute(Math.abs(data.getPrice_change().intValue()))
                .changeDirection(data.getPrice_change().intValue() > 0? "up" : "down")
                .changeRate(null) // todo: string할건지 float형 할건지 결정
                .build();
    }

    // ** 차트 응답 형식 ** //
}
