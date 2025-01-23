package com.memesphere.dashboard.service;

import com.memesphere.dashboard.dto.response.DashboardOverviewResponse;
import com.memesphere.dashboard.dto.response.DashboardTrendListResponse;

public interface DashboardQueryService {
    // ** 총 거래량 및 총 개수 ** //
    DashboardOverviewResponse getOverview();

    // ** 트렌드 ** //
    DashboardTrendListResponse getTrendList();

    // ** 차트 ** //

}
