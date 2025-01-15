package com.memesphere.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class DashboardDTO {

    @Builder
    @Getter
    public static class Trend {
        private Long coinId;
        private String image;
        private String name;
        private String symbol;
        private Integer price;
        private String direction;
        private Integer priceChange;
        private Float changeRate;
    }

    @Builder
    @Getter
    public static class TrendResponse {
        private Long totalVolume; // 등록된 밈코인의 총 거래량
        private Long totalCoin;   // 등록된 밈코인의 총 개수
        private List<Trend> trendList; // 5위까지의 밈코인 트렌드
    }

    @Builder
    @Getter
    public static class SearchVolume {
        private LocalDateTime time;
        private Integer volume; // 검색량
    }

    @Builder
    @Getter
    public static class RelatedSearchResponse {
        // 시간 당 검색 수이라고 하면 24개*2 = 48개
        private String label1;
        private List<SearchVolume> searchVolumeList1;

        private String label2;
        private List<SearchVolume> searchVolumeList2;
    }
}
