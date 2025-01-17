package com.memesphere.service.searchService;

import com.memesphere.apipayload.code.status.ErrorStatus;
import com.memesphere.apipayload.exception.GeneralException;
import com.memesphere.domain.MemeCoin;
import com.memesphere.domain.enums.SortType;
import com.memesphere.domain.enums.ViewType;
import com.memesphere.repository.MemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchQueryServiceImpl implements SearchQueryService {
    private final MemeRepository memeRepository;

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
            case MKT_CAP -> "chartData.marketCap";
            case VOLUME_24H -> "chartData.volume";
            case PRICE -> "chartData.price";
            default -> throw new GeneralException(ErrorStatus.UNSUPPORTED_SORT_TYPE);
        };

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortField));

        Page<MemeCoin> searchPage = memeRepository.findByNameContainingIgnoreCaseOrSymbolContainingIgnoreCaseOrKeywordsContainingIgnoreCase(searchWord, searchWord, searchWord, pageable);

        return searchPage;
    }
}
