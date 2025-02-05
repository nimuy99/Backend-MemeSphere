package com.memesphere.domain.binance.service;

import com.memesphere.domain.binance.dto.response.BinanceTickerResponse;

public interface BinanceQueryService {
    BinanceTickerResponse getTickerData(String symbol);
}
