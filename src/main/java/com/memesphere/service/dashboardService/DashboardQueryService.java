package com.memesphere.service.dashboardService;

import com.memesphere.dto.response.DashboardOverviewResponse;
import com.memesphere.dto.response.DashboardTrendListResponse;

public interface DashboardQueryService {
    // ** 총 거래량 및 총 개수 ** //
    DashboardOverviewResponse getOverview();

    // ** 트렌드 ** //
    DashboardTrendListResponse getTrendList();

    // ** 차트 ** //

}
