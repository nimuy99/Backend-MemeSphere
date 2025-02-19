package com.memesphere.domain.detail.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00000000")
    private Double price;

    @Schema(description = "가격 변화량", example = "-0.03")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00000000")
    private Double priceChange;

    @Schema(description = "가격 변화량 절대값", example = "0.03")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00000000")
    private Double priceChangeAbsolute;

    @Schema(description = "가격 변화 방향", example = "down")
    private String priceChangeDirection;

    @Schema(description = "가격 변화율", example = "-6.35")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00000000")
    private Double priceChangeRate;

    @Schema(description = "거래량 가중 평균 가격", example = "-942.38")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00000000")
    private Double weightedAveragePrice;

    @Schema(description = "24h 최고가", example = "2500")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00000000")
    private Double highPrice;

    @Schema(description = "24h 최저가", example = "1500")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00000000")
    private Double lowPrice;
}
