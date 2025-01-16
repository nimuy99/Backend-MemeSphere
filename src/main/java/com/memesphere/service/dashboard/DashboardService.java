package com.memesphere.service.dashboard;

import com.memesphere.dto.DashboardDTO;

public interface DashboardService {
    DashboardDTO.TrendResponse findTrendList();
    DashboardDTO.RelatedSearchResponse findRelatedSearch();
}
