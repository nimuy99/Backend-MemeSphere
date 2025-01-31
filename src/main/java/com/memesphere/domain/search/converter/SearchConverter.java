package com.memesphere.domain.search.converter;

import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.search.enums.ViewType;
import com.memesphere.domain.search.dto.response.SearchGridPreviewResponse;
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
        ChartData chartData = memeCoin.getChartDataList().get(0);

        return SearchGridPreviewResponse.builder()
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .image(memeCoin.getImage())
                .currentPrice(chartData.getPrice())
                .highPrice(chartData.getHigh_price())
                .lowPrice(chartData.getLow_price())
                .priceChange(chartData.getPriceChange())
                .priceChangeRate(chartData.getPriceChangeRate())
                .isCollected(isCollected)
                .build();
    }

    public static SearchListPreviewResponse toSearchListPreviewDTO(MemeCoin memeCoin, boolean isCollected) {
        ChartData chartData = memeCoin.getChartDataList().get(0);

        return SearchListPreviewResponse.builder()
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .currentPrice(chartData.getPrice())
                .priceChange(chartData.getPriceChangeRate())
                .weightedAveragePrice(chartData.getWeighted_average_price())
                .volume(chartData.getVolume())
                .isCollected(isCollected)
                .build();
    }
}