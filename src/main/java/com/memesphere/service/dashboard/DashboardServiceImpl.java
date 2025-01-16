package com.memesphere.service.dashboard;

import com.memesphere.dto.DashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
    @Override
    public DashboardDTO.TrendResponse findTrendList() {
        return null;
    }

    @Override
    public DashboardDTO.RelatedSearchResponse findRelatedSearch() {
        return null;
    }
}
