package com.memesphere.collection.service;

import com.memesphere.collection.domain.Collection;
import com.memesphere.collection.repository.CollectionRepository;
import com.memesphere.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectionQueryServiceImpl implements CollectionQueryService {
    private final UserRepository userRepository;
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
        return collectionRepository.findAllByUserId(userId).stream()
                .map(Collection::getMemeCoinId)
                .collect(Collectors.toList());
    }
}
