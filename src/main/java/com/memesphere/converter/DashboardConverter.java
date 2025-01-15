package com.memesphere.converter;

import com.memesphere.domain.ChartData;
import com.memesphere.domain.MemeCoin;
import com.memesphere.dto.DashboardDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DashboardConverter {
    // ** 트렌드 응답형식 **
    // 트렌드 리스트
    public static DashboardDTO.Trend toTrend(MemeCoin coin, ChartData data) {
        // 밈코인 가장 최근 차트데이터
        return DashboardDTO.Trend.builder()
                .coinId(coin.getId())
                .image(coin.getImage())
                .name(coin.getName())
                .symbol(coin.getSymbol())
                .price(data != null? data.getPrice().intValue() :null)
                .priceChange(data != null? data.getPrice_change().intValue() : null)
                .changeRate(data != null? data.getPrice_change().floatValue() : null)
                .build();
    }

    // 등록된 밈코인 수 + 거래량 + 트렌드 리스트
    public static DashboardDTO.TrendResponse toTrendResponse(long tVolume, long tCoin,
                                               List<MemeCoin> coinList,
                                               List<ChartData> dataList) {
        List<DashboardDTO.Trend> tList = IntStream.range(0, 5)
                .mapToObj(i -> toTrend(coinList.get(i), dataList.get(i)))
                .collect(Collectors.toList());

        return DashboardDTO.TrendResponse.builder()
                .totalVolume(tVolume)
                .totalCoin(tCoin)
                .trendList(tList)
                .build();
    }

    // ** 연관검색 응답형식 **
}
