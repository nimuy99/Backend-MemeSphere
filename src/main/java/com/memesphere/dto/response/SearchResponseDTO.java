package com.memesphere.dto.response;

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
        List<SearchGridPreviewDTO> gridItems; // Grid View용 데이터
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
        String name;
        String symbol;
        String image;
        BigDecimal currentPrice;
        BigDecimal highPrice;
        BigDecimal lowPrice;
        BigDecimal variation;
        Boolean isCollected;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchListPreviewDTO {
        String name;
        String symbol;
        // market cap = currentPrice x volume
        BigDecimal currentPrice;
        BigDecimal market_cap;
        Integer volume;
        BigDecimal variation;
        Boolean isCollected;
    }
}
