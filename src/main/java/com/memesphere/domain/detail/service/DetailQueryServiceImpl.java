package com.memesphere.domain.detail.service;

import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.domain.chartdata.repository.ChartDataRepository;
import com.memesphere.domain.detail.converter.DetailConverter;
import com.memesphere.domain.detail.dto.response.DetailGetResponse;
import com.memesphere.domain.detail.dto.response.PriceInfoResponse;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetailQueryServiceImpl implements DetailQueryService {
    private final MemeCoinRepository memeCoinRepository;
    private final ChartDataRepository chartDataRepository;

    @Transactional
    public DetailGetResponse getDetail(Long meme_id) {

        MemeCoin memeCoin = memeCoinRepository.findById(meme_id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        DetailGetResponse detailGetResponse = DetailConverter.toDetailGetResponse(memeCoin);

        return detailGetResponse;
    }

    public PriceInfoResponse findPriceInfo(Long coinId) {
        MemeCoin memeCoin = memeCoinRepository.findById(coinId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        ChartData data = chartDataRepository.findLatestByCoinId(coinId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHARTDATA_NOT_FOUND));

        return DetailConverter.toPriceInfoResponse(memeCoin, data);
    }
}
