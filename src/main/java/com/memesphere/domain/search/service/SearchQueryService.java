package com.memesphere.domain.search.service;

import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.search.entity.SortType;
import com.memesphere.domain.search.entity.ViewType;
import org.springframework.data.domain.Page;

public interface SearchQueryService {
    Page<MemeCoin> getSearchPage(String searchWord, ViewType viewType, SortType sortType, Integer pageNumber);
}
