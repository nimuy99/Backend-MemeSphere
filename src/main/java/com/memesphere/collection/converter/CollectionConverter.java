package com.memesphere.collection.converter;

import com.memesphere.chartdata.domain.ChartData;
import com.memesphere.collection.domain.Collection;
import com.memesphere.memecoin.domain.MemeCoin;
import com.memesphere.collection.dto.response.CollectionPageResponse;
import com.memesphere.collection.dto.response.CollectionPreviewResponse;
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
        ChartData chartData = memeCoin.getChartDataList().get(0);

        return CollectionPreviewResponse.builder()
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .image(memeCoin.getImage())
                .currentPrice(chartData.getPrice())
                .highPrice(chartData.getHigh_price())
                .lowPrice(chartData.getLow_price())
                .priceChange(chartData.getPriceChange())
                .priceChangeRate(chartData.getPriceChangeRate())
                .build();
    }
}
