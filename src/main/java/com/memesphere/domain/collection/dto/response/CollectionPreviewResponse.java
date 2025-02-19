package com.memesphere.domain.collection.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionPreviewResponse {
    @Schema(description = "밈코인 id", example = "1")
    Long coinId;
    @Schema(description = "밈코인 name", example = "도지코인")
    String name;
    @Schema(description = "밈코인 symbol", example = "DOGE")
    String symbol;
    @Schema(description = "밈코인 image", example = "https://bucketname.s3.region.amazonaws.com/image1.jpg")
    String image;
    @Schema(description = "차트 데이터의 price", example = "2000")
    BigDecimal currentPrice;
    @Schema(description = "차트 데이터의 high_price", example = "2500")
    BigDecimal highPrice;
    @Schema(description = "차트 데이터의 low_price", example = "1500")
    BigDecimal lowPrice;
    @Schema(description = "차트 데이터의 price_change", example = "500")
    BigDecimal priceChange;
    @Schema(description = "차트 데이터의 price_change_rate", example = "2.4")
    BigDecimal priceChangeRate;
}
