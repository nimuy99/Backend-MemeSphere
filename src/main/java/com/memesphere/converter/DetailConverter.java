package com.memesphere.converter;

import com.memesphere.domain.MemeCoin;
import com.memesphere.dto.response.DetailGetResponse;

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
