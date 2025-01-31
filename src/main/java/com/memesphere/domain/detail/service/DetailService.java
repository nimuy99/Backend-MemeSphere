package com.memesphere.domain.detail.service;

import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.domain.detail.converter.DetailConverter;
import com.memesphere.domain.memecoin.domain.MemeCoin;
import com.memesphere.domain.detail.dto.response.DetailGetResponse;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetailService {

    private final
    MemeCoinRepository memeCoinRepository;

    @Transactional
    public DetailGetResponse getDetail(Long meme_id) {

        MemeCoin memeCoin = memeCoinRepository.findById(meme_id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        DetailGetResponse detailGetResponse = DetailConverter.toDetailGetResponse(memeCoin);

        return detailGetResponse;
    }
}
