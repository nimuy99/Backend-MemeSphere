package com.memesphere.domain.detail.converter;

import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.detail.dto.response.DetailGetResponse;

public class DetailConverter {

    public static DetailGetResponse toDetailGetResponse(MemeCoin memeCoin) {

        return DetailGetResponse.builder()
                .id(memeCoin.getId())
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .description(memeCoin.getDescription())
                .image(memeCoin.getImage())
                .keywords(memeCoin.getKeywords())
                .collectionActive(memeCoin.getCollectionList().isEmpty())
                .build();
    }
}
