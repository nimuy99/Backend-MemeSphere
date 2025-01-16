package com.memesphere.service.searchService;

import com.memesphere.domain.MemeCoin;
import org.springframework.data.domain.Page;

public interface SearchQueryService {
    Page<MemeCoin> getSearchList(String searchWord, String viewType, Integer pageNumber);
}
