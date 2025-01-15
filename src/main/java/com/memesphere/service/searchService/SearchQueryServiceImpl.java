package com.memesphere.service.searchService;

import com.memesphere.domain.MemeCoin;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchQueryServiceImpl implements SearchQueryService {

    @Transactional(readOnly = true)
    @Override
    public Page<MemeCoin> getSearchList(String searchWord, String viewType, Integer pageNumber) {
        return null;
    }
}
