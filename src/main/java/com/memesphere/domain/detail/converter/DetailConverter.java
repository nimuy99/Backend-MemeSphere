package com.memesphere.domain.detail.converter;

import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.domain.detail.dto.response.PriceInfoResponse;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.detail.dto.response.DetailGetResponse;

import java.math.BigDecimal;

public class DetailConverter {

    public static DetailGetResponse toDetailGetResponse(MemeCoin memeCoin) {

        return DetailGetResponse.builder()
                .id(memeCoin.getId())
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .description(memeCoin.getDescription())
                .image(memeCoin.getImage())
                .keywords(memeCoin.getKeywords())
                .collectionActive(memeCoin.getCollectionList().isEmpty())
                .rank(memeCoin.getTrendRank())
                .build();
    }

    public static PriceInfoResponse toPriceInfoResponse(MemeCoin memeCoin, ChartData data) {
        return PriceInfoResponse.builder()
                .coinId(memeCoin.getId())
                .price(data.getPrice())
                .priceChange(data.getPriceChange())
                .priceChangeAbsolute(data.getPriceChange().abs())
                .priceChangeDirection(data.getPriceChangeRate().compareTo(BigDecimal.ZERO) < 0 ? "down" : "up")
                .priceChangeRate(data.getPriceChangeRate())
                .weightedAveragePrice(data.getWeighted_average_price())
                .highPrice(data.getHigh_price())
                .lowPrice(data.getLow_price())
                .build();
    }
}
