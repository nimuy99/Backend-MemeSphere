package com.memesphere.domain.chartdata.converter;

import com.memesphere.binance.dto.response.BinanceTickerResponse;
import com.memesphere.domain.chartdata.domain.ChartData;
import com.memesphere.domain.memecoin.domain.MemeCoin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ChartDataConverter {

    public static ChartData toChartData(MemeCoin memeCoin, BinanceTickerResponse response) {
        return ChartData.builder()
                .memeCoin(memeCoin)
                .recordedTime(LocalDateTime.now())
                .price(new BigDecimal(response.getLastPrice()))
                .priceChange(new BigDecimal(response.getPriceChange()))
                .priceChangeRate(new BigDecimal(response.getPriceChangePercent()))
                .weighted_average_price(new BigDecimal(response.getWeightedAvgPrice()))
                .volume(new BigDecimal(response.getVolume()))
                .low_price(new BigDecimal(response.getLowPrice()))
                .high_price(new BigDecimal(response.getHighPrice()))
                .build();
    }
}
