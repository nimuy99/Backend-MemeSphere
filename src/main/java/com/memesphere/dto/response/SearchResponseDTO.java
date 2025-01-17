package com.memesphere.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class SearchResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchPageDTO {
        @Schema(description = "gridView용 검색 아이템들")
        List<SearchGridPreviewDTO> gridItems; // Grid View용 데이터
        @Schema(description = "listView용 검색 아이템들")
        List<SearchListPreviewDTO> listItems; // List View용 데이터
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
    public static class SearchGridPreviewDTO {
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
        @Schema(description = "collection에 해당 밈코인 유무")
        Boolean isCollected;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchListPreviewDTO {
        @Schema(description = "밈코인 name")
        String name;
        @Schema(description = "밈코인 symbol")
        String symbol;
        // market cap = currentPrice x volume
        @Schema(description = "차트 데이터의 price")
        BigDecimal currentPrice;
        @Schema(description = "차트 데이터의 marketCap")
        BigDecimal market_cap;
        @Schema(description = "차트 데이터의 volume")
        Integer volume;
        @Schema(description = "차트 데이터의 price_change")
        BigDecimal variation;
        @Schema(description = "collection에 해당 밈코인 유무")
        Boolean isCollected;
    }
}
