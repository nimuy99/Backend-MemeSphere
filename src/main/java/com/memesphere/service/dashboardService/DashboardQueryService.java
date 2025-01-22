package com.memesphere.service.dashboardService;

import com.memesphere.domain.enums.SortType;
import com.memesphere.domain.enums.ViewType;
import com.memesphere.dto.response.DashboardOverviewResponse;
import com.memesphere.dto.response.DashboardTrendListResponse;
import com.memesphere.dto.response.SearchPageResponse;


public interface DashboardQueryService {
    // ** 총 거래량 및 총 개수 ** //
    DashboardOverviewResponse getOverview();

    // ** 트렌드 ** //
    DashboardTrendListResponse getTrendList();

    // ** 차트 ** //
    SearchPageResponse getChartPage(Long userId, ViewType viewType, SortType sortType, Integer pageNumber);
}
