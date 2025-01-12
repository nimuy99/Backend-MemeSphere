package com.memesphere.service.collectionService;

import com.memesphere.domain.Collection;
import com.memesphere.domain.User;
import com.memesphere.repository.CollectionRepository;
import com.memesphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollectionQueryServiceImpl implements CollectionQueryService {
    private final UserRepository userRepository;
    private final CollectionRepository collectionRepository;

    @Transactional
    @Override
    public Page<Collection> getCollectionList(Long userId, Integer pageNumber) {
        User user = userRepository.findById(userId).get();
        Page<Collection> collectionPage = collectionRepository.findAllByUser(user, PageRequest.of(pageNumber, 9 ));
        return collectionPage;
    }
}
