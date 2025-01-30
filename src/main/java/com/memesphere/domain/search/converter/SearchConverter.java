package com.memesphere.domain.search.converter;

import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.search.dto.response.SearchGridPreviewResponse;
import com.memesphere.domain.search.entity.ViewType;
import com.memesphere.domain.search.dto.response.SearchListPreviewResponse;
import com.memesphere.domain.search.dto.response.SearchPageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class SearchConverter {
    public static SearchPageResponse toSearchPageDTO(Page<MemeCoin> searchPage, ViewType viewType, List<Long> userCollectionIds) {
        List<SearchGridPreviewResponse> gridItems = null;
        List<SearchListPreviewResponse> listItems = null;

        if (viewType == ViewType.GRID) {
            gridItems = searchPage.stream()
                    .map(memeCoin -> toSearchGridPreviewDTO(memeCoin, userCollectionIds.contains(memeCoin.getId())))
                    .collect(Collectors.toList());
        } else if (viewType == ViewType.LIST) {
            listItems = searchPage.stream()
                    .map(memeCoin -> toSearchListPreviewDTO(memeCoin, userCollectionIds.contains(memeCoin.getId())))
                    .collect(Collectors.toList());
        }

        return SearchPageResponse.builder()
                .gridItems(gridItems)
                .listItems(listItems)
                .listSize(searchPage.getContent().size())
                .totalPage(searchPage.getTotalPages())
                .totalElements(searchPage.getTotalElements())
                .isFirst(searchPage.isFirst())
                .isLast(searchPage.isLast())
                .build();
    }

    public static SearchGridPreviewResponse toSearchGridPreviewDTO(MemeCoin memeCoin, boolean isCollected) {
        return SearchGridPreviewResponse.builder()
                .coinId(memeCoin.getId())
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .image(memeCoin.getImage())
                .currentPrice(memeCoin.getChartData().getPrice())
                .highPrice(memeCoin.getChartData().getHigh_price())
                .lowPrice(memeCoin.getChartData().getLow_price())
                .priceChange(memeCoin.getChartData().getPriceChange())
                .priceChangeRate(memeCoin.getChartData().getPriceChangeRate())
                .isCollected(isCollected)
                .build();
    }

    public static SearchListPreviewResponse toSearchListPreviewDTO(MemeCoin memeCoin, boolean isCollected) {
        return SearchListPreviewResponse.builder()
                .coinId(memeCoin.getId())
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .currentPrice(memeCoin.getChartData().getPrice())
                .priceChange(memeCoin.getChartData().getPriceChangeRate())
                .weightedAveragePrice(memeCoin.getChartData().getWeighted_average_price())
                .volume(memeCoin.getChartData().getVolume())
                .isCollected(isCollected)
                .build();
    }
}