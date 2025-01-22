package com.memesphere.service;

import com.memesphere.apipayload.code.status.ErrorStatus;
import com.memesphere.apipayload.exception.GeneralException;
import com.memesphere.converter.DetailConverter;
import com.memesphere.domain.MemeCoin;
import com.memesphere.dto.response.DetailGetResponse;
import com.memesphere.repository.MemeCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetailService {

    private final MemeCoinRepository memeCoinRepository;

    @Transactional
    public DetailGetResponse getDetail(Long meme_id) {

        MemeCoin memeCoin = memeCoinRepository.findById(meme_id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        DetailGetResponse detailGetResponse = DetailConverter.toDetailGetResponse(memeCoin);

        return detailGetResponse;
    }
}
