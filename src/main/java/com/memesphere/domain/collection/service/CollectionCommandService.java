package com.memesphere.domain.collection.service;

import com.memesphere.domain.collection.entity.Collection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CollectionCommandService {
    String addCollectCoin(Long userId, Long coinId);
    String removeCollectCoin(Long userId, Long coinId);
}
