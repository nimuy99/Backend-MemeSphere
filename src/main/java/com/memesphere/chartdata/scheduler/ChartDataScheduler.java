package com.memesphere.chartdata.scheduler;

import com.memesphere.binance.dto.response.BinanceTickerResponse;
import com.memesphere.binance.service.BinanceQueryService;
import com.memesphere.chartdata.domain.ChartData;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.memecoin.domain.MemeCoin;
import com.memesphere.memecoin.repository.MemeCoinRepository;
import com.memesphere.memecoin.service.MemeCoinQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.memesphere.chartdata.converter.ChartDataConverter.toChartData;

@Component
@RequiredArgsConstructor
public class ChartDataScheduler {
    private final MemeCoinRepository memeCoinRepository;
    private final BinanceQueryService binanceQueryService;
    private final MemeCoinQueryService memeCoinQueryService;

    @Scheduled(fixedRate = 600000) // 10분 간격 실행
    @Transactional
    public void updateChartData() {
        List<MemeCoin> memeCoins = memeCoinRepository.findAll();

        for (MemeCoin memeCoin : memeCoins) {
            try {
                String symbol = memeCoin.getSymbol() + "USDT";
                BinanceTickerResponse response = binanceQueryService.getTickerData(symbol);

                ChartData chartData = toChartData(memeCoin,response);

                memeCoinQueryService.updateChartData(memeCoin.getId(), chartData);

            } catch (Exception e) {
                throw new GeneralException(ErrorStatus.CANNOT_LOAD_CHARTDATA);
            }
        }
    }
}


