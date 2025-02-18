package com.memesphere.domain.naver.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchRequest {

    @Schema(description = "조회 기간 시작 날짜", example = "2025-02-07")
    private String startDate;

    @Schema(description = "조회 기간 종료 날짜", example = "2025-02-14")
    private String endDate;

    @Schema(description = "구간 단위", example = "date")
    private String timeUnit;

    @Schema(description = "검색어 그룹")
    private List<com.memesphere.domain.naver.dto.request.SearchRequest.KeywordGroup> keywordGroups;

    @Getter
    @Builder
    public static class KeywordGroup {
        @Schema(description = "그룹 이름", example = "밈코인")
        private String groupName;

        @Schema(description = "조회할 검색어", example = "[\"도지코인\"]")
        private List<String> keywords;
    }
}

