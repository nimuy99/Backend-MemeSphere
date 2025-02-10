package com.memesphere.domain.collection.service;

import com.memesphere.domain.collection.converter.CollectionConverter;
import com.memesphere.domain.collection.entity.Collection;
import com.memesphere.domain.collection.repository.CollectionRepository;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import com.memesphere.domain.user.entity.User;
import com.memesphere.domain.user.repository.UserRepository;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollectionCommandServiceImpl implements CollectionCommandService {
    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final MemeCoinRepository coinRepository;

    public String addCollectCoin(Long userId, Long coinId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        MemeCoin coin = coinRepository.findById(coinId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        // 이미 등록한 콜렉션의 경우
        if (collectionRepository.findByUserAndMemeCoin(user, coin).isPresent()) {
            throw new GeneralException(ErrorStatus.COLLECTION_ALREADY_EXISTS);
        }

        // 새로운 컬렉션 저장
        Collection collection = CollectionConverter.toCollection(user, coin);
        collectionRepository.save(collection);

        return "[coinId] " + coinId + " 등록 완료.";
    }

    @Override
    public String removeCollectCoin(Long userId, Long coinId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        MemeCoin coin = coinRepository.findById(coinId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        // 이미 삭제한 콜렉션의 경우
        Collection collection = collectionRepository.findByUserAndMemeCoin(user, coin)
                .orElseThrow(() -> new GeneralException(ErrorStatus.COLLECTION_NOT_FOUND));

        collectionRepository.delete(collection);

        return "[coinId] " + coinId + " 삭제 완료.";
    }

}
