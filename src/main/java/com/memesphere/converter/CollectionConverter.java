package com.memesphere.converter;

import com.memesphere.domain.Collection;
import com.memesphere.domain.MemeCoin;
import com.memesphere.dto.response.CollectionPageResponse;
import com.memesphere.dto.response.CollectionPreviewResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CollectionConverter {
    public static CollectionPageResponse toCollectionPageDTO(Page<Collection> collectionPage) {
        List<CollectionPreviewResponse> collectionItems = collectionPage.getContent().stream()
                .map(collection -> toCollectionPreviewDTO(collection))
                .collect(Collectors.toList());

        return CollectionPageResponse.builder()
                .collectionItems(collectionItems)
                .listSize(collectionItems.size())
                .totalPage(collectionPage.getTotalPages())
                .totalElements(collectionPage.getTotalElements())
                .isFirst(collectionPage.isFirst())
                .isLast(collectionPage.isLast())
                .build();
    }

    private static CollectionPreviewResponse toCollectionPreviewDTO(Collection collection) {
        MemeCoin memeCoin = collection.getMemeCoin();

        return CollectionPreviewResponse.builder()
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .image(memeCoin.getImage())
                .currentPrice(memeCoin.getChartData().getPrice())
                .highPrice(memeCoin.getChartData().getHigh_price())
                .lowPrice(memeCoin.getChartData().getLow_price())
                .priceChange(memeCoin.getChartData().getPriceChange())
                .priceChangeRate(memeCoin.getChartData().getPriceChangeRate())
                .build();
    }
}
