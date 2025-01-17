package com.memesphere.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(description = "콜렉션 아이템들")
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
        @Schema(description = "밈코인 name")
        String name;
        @Schema(description = "밈코인 symbol")
        String symbol;
        @Schema(description = "밈코인 image")
        String image;
        @Schema(description = "차트 데이터의 price")
        BigDecimal currentPrice;
        @Schema(description = "차트 데이터의 high_price")
        BigDecimal highPrice;
        @Schema(description = "차트 데이터의 low_price")
        BigDecimal lowPrice;
        @Schema(description = "차트 데이터의 price_change")
        BigDecimal variation;
    }
}
