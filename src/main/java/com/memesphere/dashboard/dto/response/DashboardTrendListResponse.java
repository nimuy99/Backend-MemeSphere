package com.memesphere.dashboard.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DashboardTrendListResponse {
    @Schema(description = "5위까지의 밈코인 트렌드")
    private List<DashboardTrendResponse> trendList;
}
