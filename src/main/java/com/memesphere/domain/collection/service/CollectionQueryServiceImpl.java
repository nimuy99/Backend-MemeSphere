package com.memesphere.domain.collection.service;

import com.memesphere.domain.collection.entity.Collection;
import com.memesphere.domain.collection.repository.CollectionRepository;
import com.memesphere.domain.search.entity.SortType;
import com.memesphere.domain.search.entity.ViewType;
import com.memesphere.domain.user.repository.UserRepository;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Collection> getCollectionPage(Long userId, Integer pageNumber, ViewType viewType, SortType sortType) {

        int pageSize = switch (viewType) {
            case GRID -> 12;
            case LIST -> 20;
            default -> throw new GeneralException(ErrorStatus.UNSUPPORTED_VIEW_TYPE);
        };

        String sortField = switch (sortType) {
            case PRICE_CHANGE -> "priceChange";
            case VOLUME_24H -> "volume";
            case PRICE -> "price";
            default -> throw new GeneralException(ErrorStatus.UNSUPPORTED_SORT_TYPE);
        };

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Collection> collectionPage = collectionRepository.findWithLatestChartDataSorted(userId, sortField, pageable);
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
