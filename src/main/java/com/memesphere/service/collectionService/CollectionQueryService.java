package com.memesphere.service.collectionService;

import com.memesphere.domain.Collection;
import org.springframework.data.domain.Page;

public interface CollectionQueryService {
    Page<Collection> getCollectionList(Long userId, Integer pageNumber);
}
