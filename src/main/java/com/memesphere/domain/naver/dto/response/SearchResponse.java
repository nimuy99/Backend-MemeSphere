package com.memesphere.domain.naver.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchResponse {
    @Schema(description = "조회 기간 시작 날짜", example = "2025-02-07")
    private String startDate;

    @Schema(description = "조회 기간 종료 날짜", example = "2025-02-13")
    private String endDate;

    @Schema(description = "구간 단위", example = "date")
    private String timeUnit;

    private List<Result> results;

    @Getter
    @Builder
    public static class Result {
        private String title;
        private List<String> keywords;
        private List<Data> data;

        @Getter
        @Builder
        public static class Data {
            @Schema(description = "구간별 시작 날짜", example = "2025-02-14")
            private String period;

            @Schema(description = "구간별 검색량의 상대적 비율", example = "70.2")
            private double ratio;
        }
    }
}

