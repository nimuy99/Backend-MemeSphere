package com.memesphere.domain.search.service;

import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import com.memesphere.domain.search.entity.SortType;
import com.memesphere.domain.search.entity.ViewType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchQueryServiceImpl implements SearchQueryService {
    private final MemeCoinRepository memeCoinRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<MemeCoin> getSearchPage(String searchWord, ViewType viewType, SortType sortType, Integer pageNumber) {
        // searchWord --> range: name, symbol, keywords of memecoin
        // viewType --> GRID(9 items per page), LIST(20 items per page)
        // sortType --> MKT_CAP, VOLUME_24H, PRICE

        int pageSize = switch (viewType) {
            case GRID -> 9;
            case LIST -> 20;
            default -> throw new GeneralException(ErrorStatus.UNSUPPORTED_VIEW_TYPE);
        };

        String sortField = switch (sortType) {
            case PRICE_CHANGE -> "priceChange";
            case VOLUME_24H -> "volume";
            case PRICE -> "price";
            default -> throw new GeneralException(ErrorStatus.UNSUPPORTED_SORT_TYPE);
        };

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<MemeCoin> searchPage = memeCoinRepository.findWithLatestChartDataSorted(searchWord, sortField, pageable);;

        return searchPage;
    }
}
