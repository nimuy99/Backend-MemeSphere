package com.memesphere.converter;

import com.memesphere.domain.MemeCoin;
import com.memesphere.domain.enums.ViewType;
import com.memesphere.dto.response.SearchResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class SearchConverter {
    public static SearchResponseDTO.SearchPageDTO toSearchPageDTO(Page<MemeCoin> searchPage, ViewType viewType, List<Long> userCollectionIds) {
        List<SearchResponseDTO.SearchGridPreviewDTO> gridItems = null;
        List<SearchResponseDTO.SearchListPreviewDTO> listItems = null;

        if (viewType == ViewType.GRID) {
            gridItems = searchPage.stream()
                    .map(memeCoin -> toSearchGridPreviewDTO(memeCoin, userCollectionIds.contains(memeCoin.getId())))
                    .collect(Collectors.toList());
        } else if (viewType == ViewType.LIST) {
            listItems = searchPage.stream()
                    .map(memeCoin -> toSearchListPreviewDTO(memeCoin, userCollectionIds.contains(memeCoin.getId())))
                    .collect(Collectors.toList());
        }

        return SearchResponseDTO.SearchPageDTO.builder()
                .gridItems(gridItems)
                .listItems(listItems)
                .listSize(searchPage.getContent().size())
                .totalPage(searchPage.getTotalPages())
                .totalElements(searchPage.getTotalElements())
                .isFirst(searchPage.isFirst())
                .isLast(searchPage.isLast())
                .build();
    }

    public static SearchResponseDTO.SearchGridPreviewDTO toSearchGridPreviewDTO(MemeCoin memeCoin, boolean isCollected) {
        return SearchResponseDTO.SearchGridPreviewDTO.builder()
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .image(memeCoin.getImage())
                .currentPrice(memeCoin.getCurrent_price())
                .highPrice(memeCoin.getHigh_price())
                .lowPrice(memeCoin.getLow_price())
                .variation(memeCoin.getVariation())
                .isCollected(isCollected)
                .build();
    }

    public static SearchResponseDTO.SearchListPreviewDTO toSearchListPreviewDTO(MemeCoin memeCoin, boolean isCollected) {
        return SearchResponseDTO.SearchListPreviewDTO.builder()
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .currentPrice(memeCoin.getCurrent_price())
                .market_cap(memeCoin.getMarket_cap())
                .volume(memeCoin.getVolume())
                .isCollected(isCollected)
                .build();
    }
}