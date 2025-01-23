package com.memesphere.search.service;

import com.memesphere.memecoin.domain.MemeCoin;
import com.memesphere.search.enums.SortType;
import com.memesphere.search.enums.ViewType;
import org.springframework.data.domain.Page;

public interface SearchQueryService {
    Page<MemeCoin> getSearchPage(String searchWord, ViewType viewType, SortType sortType, Integer pageNumber);
}
