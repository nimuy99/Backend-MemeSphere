package com.memesphere.service.dashboardService;

import com.memesphere.apipayload.code.status.ErrorStatus;
import com.memesphere.apipayload.exception.GeneralException;
import com.memesphere.converter.DashboardConverter;
import com.memesphere.converter.SearchConverter;
import com.memesphere.domain.ChartData;
import com.memesphere.domain.MemeCoin;
import com.memesphere.domain.enums.SortType;
import com.memesphere.domain.enums.ViewType;
import com.memesphere.dto.response.DashboardOverviewResponse;
import com.memesphere.dto.response.DashboardTrendListResponse;
import com.memesphere.dto.response.SearchPageResponse;
import com.memesphere.repository.ChartDataRepository;
import com.memesphere.repository.MemeCoinRepository;
import com.memesphere.service.collectionService.CollectionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashboardQueryServiceImpl implements DashboardQueryService {
    private final MemeCoinRepository memeCoinRepository;
    private final ChartDataRepository chartDataRepository;
    private final CollectionQueryService collectionQueryService;

    // ** 총 거래량 및 총 개수 ** //
    @Override
    public DashboardOverviewResponse getOverview() {
        // 등록된 모든 밈코인의 총 거래량
        Long totalVolume = chartDataRepository.findTotalVolume();

        // 등록된 모든 밈코인 수
        Long totalCoin = memeCoinRepository.count();

        return DashboardConverter.toOverView(totalVolume, totalCoin);
    }

    // ** 트렌드 ** //
    @Override
    public DashboardTrendListResponse getTrendList() {
        // 거래량 top5 밈코인-차트데이터
        List<ChartData> dataList = chartDataRepository.findTop5ByOrderByVolumeDesc();

        return DashboardConverter.toTrendList(dataList);
    }

    // ** 차트 ** //
    @Override
    public SearchPageResponse getChartPage(Long userId, ViewType viewType, SortType sortType, Integer pageNumber) {
        // TODO: 유저 받아와서 예외처리
        List<Long> userCollectionIds = collectionQueryService.getUserCollectionIds(userId);

        // viewType --> GRID(9 items per page), LIST(20 items per page)
        // sortType --> MKT_CAP, VOLUME_24H, PRICE

        int pageSize = switch (viewType) {
            case GRID -> 9;
            case LIST -> 20;
            default -> throw new GeneralException(ErrorStatus.UNSUPPORTED_VIEW_TYPE);
        };

        String sortField = switch (sortType) {
            case MKT_CAP -> "chartData.marketCap";
            case VOLUME_24H -> "chartData.volume";
            case PRICE -> "chartData.price";
            default -> throw new GeneralException(ErrorStatus.UNSUPPORTED_SORT_TYPE);
        };

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortField));
        Page<MemeCoin> coinPage = memeCoinRepository.findAll(pageable);

        // null 체크 후 예외 처리}
        return SearchConverter.toSearchPageDTO(coinPage, viewType, userCollectionIds);
    }
}
