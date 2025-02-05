package com.memesphere.domain.collection.service;

import com.memesphere.domain.collection.entity.Collection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CollectionCommandService {
    String addCollectCoin(String email, Long coinId);
    String removeCollectCoin(String email, Long coinId);
}
