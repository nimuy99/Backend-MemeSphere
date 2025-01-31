package com.memesphere.binance.service;

import com.memesphere.binance.dto.response.BinanceTickerResponse;

public interface BinanceQueryService {
    BinanceTickerResponse getTickerData(String symbol);
}
