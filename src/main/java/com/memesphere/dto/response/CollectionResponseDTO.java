package com.memesphere.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class CollectionResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectionPageDTO {
        List<CollectionPreviewDTO> collectionItems;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectionPreviewDTO {
        String name;
        String symbol;
        String image;
        BigDecimal currentPrice;
        BigDecimal highPrice;
        BigDecimal lowPrice;
        BigDecimal variation;
    }
}
