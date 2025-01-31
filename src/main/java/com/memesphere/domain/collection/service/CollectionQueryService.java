package com.memesphere.domain.collection.service;

import com.memesphere.domain.collection.entity.Collection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CollectionQueryService {
    Page<Collection> getCollectionPage(Long userId, Integer pageNumber);
    List<Long> getUserCollectionIds(Long userId);
}
