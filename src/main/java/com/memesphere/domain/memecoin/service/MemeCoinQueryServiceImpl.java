package com.memesphere.domain.memecoin.service;

import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.domain.chartdata.service.ChartDataQueryService;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemeCoinQueryServiceImpl implements MemeCoinQueryService {
    private final MemeCoinRepository memeCoinRepository;
    private final ChartDataQueryService chartDataService;

    @Transactional
    @Override
    public void updateChartData(Long memeCoinId, ChartData newChartData) {
        MemeCoin memeCoin = memeCoinRepository.findById(memeCoinId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        chartDataService.saveChartData(memeCoin, newChartData);
    }
}
