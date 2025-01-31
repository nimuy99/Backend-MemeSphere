package com.memesphere.domain.search.service;

import com.memesphere.domain.memecoin.domain.MemeCoin;
import com.memesphere.domain.search.enums.SortType;
import com.memesphere.domain.search.enums.ViewType;
import org.springframework.data.domain.Page;

public interface SearchQueryService {
    Page<MemeCoin> getSearchPage(String searchWord, ViewType viewType, SortType sortType, Integer pageNumber);
}
