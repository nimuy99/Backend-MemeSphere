package com.memesphere.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchPageResponse {
    @Schema(description = "gridView용 검색 아이템들")
    List<SearchGridPreviewResponse> gridItems; // Grid View용 데이터
    @Schema(description = "listView용 검색 아이템들")
    List<SearchListPreviewResponse> listItems; // List View용 데이터
    Integer listSize;
    Integer totalPage;
    Long totalElements;
    Boolean isFirst;
    Boolean isLast;
}
