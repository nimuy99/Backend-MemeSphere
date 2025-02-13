package com.memesphere.domain.detail.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DetailGetResponse {

    @Schema(description = "밈코인 아이디", example = "1")
    private Long id;

    @Schema(description = "이름", example = "도지코인")
    private String name;

    @Schema(description = "심볼", example = "DOGE")
    private String symbol;

    @Schema(description = "밈코인 설명", example = "빌리 마커스와 잭슨 팔머가 2013년 12월에 라이트코인을 기반으로 만든 오픈 소스 디지털 화폐입니다.")
    private String description;

    @Schema(description = "밈코인 이미지", example = "https://bucketname.s3.region.amazonaws.com/image1.jpg")
    private String image;

    @Schema(description = "키워드", example = "['활발한 거래', 'HOT']")
    private List<String> keywords;

    @Schema(description = "컬렉션 유무", example = "True")
    private boolean collectionActive;

    @Schema(description = "밈코인 순위", example = "1")
    private Integer rank;
}
