package com.memesphere.binance.service;

import com.memesphere.binance.dto.response.BinanceTickerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BinanceQueryServiceImpl implements BinanceQueryService {
    private final RestTemplate restTemplate;

    private static final String BINANCE_API_URL = "https://api.binance.com/api/v3/ticker/24hr?symbol=";

    @Override
    @Transactional
    public BinanceTickerResponse getTickerData(String symbol) {
        String url = BINANCE_API_URL + symbol;
        return restTemplate.getForObject(url, BinanceTickerResponse.class);
    }
}
