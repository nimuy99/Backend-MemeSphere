package com.memesphere.service.collectionService;

import com.memesphere.domain.Collection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CollectionQueryService {
    Page<Collection> getCollectionPage(Long userId, Integer pageNumber);
    List<Long> getUserCollectionIds(Long userId);
}
