package com.memesphere.service.searchService;

import com.memesphere.domain.MemeCoin;
import com.memesphere.domain.enums.SortType;
import com.memesphere.domain.enums.ViewType;
import org.springframework.data.domain.Page;

public interface SearchQueryService {
    Page<MemeCoin> getSearchPage(String searchWord, ViewType viewType, SortType sortType, Integer pageNumber);
}
