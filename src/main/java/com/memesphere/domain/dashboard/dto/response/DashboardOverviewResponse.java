package com.memesphere.domain.dashboard.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Getter
@Builder
public class DashboardOverviewResponse {
    @Schema(description = "등록된 밈코인의 총 거래량", example = "32,431,877,180,618.15")
    private BigDecimal totalVolume;

    @Schema(description = "등록된 밈코인의 총 개수", example = "125")
    private Long totalCoin;
}
