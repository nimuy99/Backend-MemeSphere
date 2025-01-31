package com.memesphere.domain.dashboard.service;

import com.memesphere.domain.dashboard.dto.response.DashboardOverviewResponse;
import com.memesphere.domain.dashboard.dto.response.DashboardTrendListResponse;

public interface DashboardQueryService {
    // ** 총 거래량 및 총 개수 ** //
    DashboardOverviewResponse getOverview();

    // ** 트렌드 ** //
    DashboardTrendListResponse getTrendList();

    // ** 차트 ** //

}
