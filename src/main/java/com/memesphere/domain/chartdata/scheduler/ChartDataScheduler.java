package com.memesphere.domain.chartdata.scheduler;

import com.memesphere.domain.binance.dto.response.BinanceTickerResponse;
import com.memesphere.domain.binance.service.BinanceQueryService;
import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import com.memesphere.domain.memecoin.service.MemeCoinQueryService;
import com.memesphere.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.memesphere.domain.chartdata.converter.ChartDataConverter.toChartData;

@Component
@RequiredArgsConstructor
public class ChartDataScheduler {
    private final MemeCoinRepository memeCoinRepository;
    private final BinanceQueryService binanceQueryService;
    private final MemeCoinQueryService memeCoinQueryService;

    @Scheduled(cron = "0 0/1 * * * ?") // 0, 10, 20, 30, 40, 50분에 실행
    @Transactional
    public void updateChartData() {

        Long userId = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // `authentication.getPrincipal()`을 `CustomUserDetails`로 변환
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            userId = userDetails.getUser().getId();
        }
        List<MemeCoin> memeCoins = memeCoinRepository.findAll();

        for (MemeCoin memeCoin : memeCoins) {
            try {
                String symbol = memeCoin.getSymbol() + "USDT";
                BinanceTickerResponse response = binanceQueryService.getTickerData(symbol);

                ChartData chartData = toChartData(memeCoin,response);

                memeCoinQueryService.updateChartData(memeCoin.getId(), chartData, userId);

            } catch (Exception e) {
                throw new GeneralException(ErrorStatus.CANNOT_LOAD_CHARTDATA);
            }
        }
    }
}


