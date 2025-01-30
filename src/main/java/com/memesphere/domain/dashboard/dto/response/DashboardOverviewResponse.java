package com.memesphere.domain.dashboard.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardOverviewResponse {
    @Schema(description = "등록된 밈코인의 총 거래량", example = "29960")
    private Long totalVolume;

    @Schema(description = "등록된 밈코인의 총 개수", example = "125")
    private Long totalCoin;
}
