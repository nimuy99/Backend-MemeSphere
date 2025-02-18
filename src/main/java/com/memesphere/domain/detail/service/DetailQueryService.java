package com.memesphere.domain.detail.service;


import com.memesphere.domain.detail.dto.response.DetailGetResponse;
import com.memesphere.domain.detail.dto.response.PriceInfoResponse;

public interface DetailQueryService {
    DetailGetResponse getDetail(Long meme_id);
    PriceInfoResponse findPriceInfo(Long coinId);
}
