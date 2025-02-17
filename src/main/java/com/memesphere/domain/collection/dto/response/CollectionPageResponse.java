package com.memesphere.domain.collection.dto.response;

import com.memesphere.domain.collection.entity.Collection;
import com.memesphere.domain.search.dto.response.SearchGridPreviewResponse;
import com.memesphere.domain.search.dto.response.SearchListPreviewResponse;
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
public class CollectionPageResponse {
    @Schema(description = "gridView용 컬렉션 아이템들")
    List<CollectionGridPreviewResponse> gridItems; // Grid View용 데이터
    @Schema(description = "listView용 컬렉션 아이템들")
    List<CollectionListPreviewResponse> listItems; // List View용 데이터
    Integer listSize;
    Integer totalPage;
    Long totalElements;
    Boolean isFirst;
    Boolean isLast;

}
