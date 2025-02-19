package com.memesphere.domain.collection.service;

import com.memesphere.domain.collection.entity.Collection;
import com.memesphere.domain.collection.repository.CollectionRepository;
import com.memesphere.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectionQueryServiceImpl implements CollectionQueryService {
    private final CollectionRepository collectionRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Collection> getCollectionPage(Long userId, Integer pageNumber) {
        Page<Collection> collectionPage = collectionRepository.findAllByUserId(userId, PageRequest.of(pageNumber, 9 ));
        return collectionPage;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> getUserCollectionIds(Long userId) {
        if (userId == null) return Collections.emptyList();

        return collectionRepository.findAllByUserId(userId).stream()
                .map(Collection::getMemeCoinId)
                .collect(Collectors.toList());
    }
}
