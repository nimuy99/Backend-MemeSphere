package com.memesphere.domain.detail.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PriceInfoResponse {
    @Schema(description = "밈코인 아이디", example = "1")
    private Long coinId;

    @Schema(description = "현재가", example = "0.20")
    private BigDecimal price;

    @Schema(description = "가격 변화량", example = "-0.03")
    private BigDecimal priceChange;

    @Schema(description = "가격 변화량 절대값", example = "0.03")
    private BigDecimal priceChangeAbsolute;

    @Schema(description = "가격 변화 방향", example = "down")
    private String priceChangeDirection;

    @Schema(description = "가격 변화율", example = "-6.35")
    private BigDecimal priceChangeRate;

    @Schema(description = "거래량 가중 평균 가격", example = "-942.38")
    private BigDecimal weightedAveragePrice;

    @Schema(description = "24h 최고가", example = "2500")
    private BigDecimal highPrice;

    @Schema(description = "24h 최저가", example = "1500")
    private BigDecimal lowPrice;
}
