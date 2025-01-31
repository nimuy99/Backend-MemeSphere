package com.memesphere.binance.dto.response;

import lombok.Data;

@Data
public class BinanceTickerResponse {
    private String symbol;
    private String priceChange;
    private String priceChangePercent;
    private String weightedAvgPrice;
    private String lastPrice;
    private String highPrice;
    private String lowPrice;
    private String volume;
}
