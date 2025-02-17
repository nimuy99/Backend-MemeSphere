package com.memesphere.domain.collection.service;

import com.memesphere.domain.collection.entity.Collection;
import com.memesphere.domain.search.entity.SortType;
import com.memesphere.domain.search.entity.ViewType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CollectionQueryService {
    Page<Collection> getCollectionPage(Long userId, Integer pageNumber, ViewType viewType, SortType sortType);
    List<Long> getUserCollectionIds(Long userId);
}
