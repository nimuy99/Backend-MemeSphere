package com.memesphere.dto.response;

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
public class SearchListPreviewResponse {
    @Schema(description = "밈코인 name", example = "도지코인")
    String name;
    @Schema(description = "밈코인 symbol", example = "DOGE")
    String symbol;
    // market cap = currentPrice x volume
    @Schema(description = "차트 데이터의 price", example = "2000")
    BigDecimal currentPrice;
    @Schema(description = "차트 데이터의 marketCap", example = "10000")
    BigDecimal market_cap;
    @Schema(description = "차트 데이터의 volume", example = "5")
    Integer volume;
    @Schema(description = "차트 데이터의 price_change", example = "500")
    BigDecimal variation;
    @Schema(description = "collection에 해당 밈코인 유무", example = "true / false")
    Boolean isCollected;
}
