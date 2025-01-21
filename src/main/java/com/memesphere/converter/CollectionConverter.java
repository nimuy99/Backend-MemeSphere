package com.memesphere.converter;

import com.memesphere.domain.Collection;
import com.memesphere.domain.MemeCoin;
import com.memesphere.dto.response.CollectionResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CollectionConverter {
    public static CollectionResponseDTO.CollectionPageDTO toCollectionPageDTO(Page<Collection> collectionPage) {
        List<CollectionResponseDTO.CollectionPreviewDTO> collectionItems = collectionPage.getContent().stream()
                .map(collection -> toCollectionPreviewDTO(collection))
                .collect(Collectors.toList());

        return CollectionResponseDTO.CollectionPageDTO.builder()
                .collectionItems(collectionItems)
                .listSize(collectionItems.size())
                .totalPage(collectionPage.getTotalPages())
                .totalElements(collectionPage.getTotalElements())
                .isFirst(collectionPage.isFirst())
                .isLast(collectionPage.isLast())
                .build();
    }

    private static CollectionResponseDTO.CollectionPreviewDTO toCollectionPreviewDTO(Collection collection) {
        MemeCoin memeCoin = collection.getMemeCoin();

        return CollectionResponseDTO.CollectionPreviewDTO.builder()
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .image(memeCoin.getImage())
                .currentPrice(memeCoin.getCurrent_price())
                .highPrice(memeCoin.getHigh_price())
                .lowPrice(memeCoin.getLow_price())
                .variation(memeCoin.getVariation())
                .build();
    }
}
