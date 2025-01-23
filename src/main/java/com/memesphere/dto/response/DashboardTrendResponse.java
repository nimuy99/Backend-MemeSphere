package com.memesphere.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardTrendResponse {
    @Schema(description = "밈코인 아이디", example = "1")
    private Long coinId;

    @Schema(description = "이미지", example = "https://bucketname.s3.region.amazonaws.com/image1.jpg")
    private String image;

    @Schema(description = "이름", example = "도지")
    private String name;

    @Schema(description = "심볼", example = "DOGE")
    private String symbol;

    @Schema(description = "현재가", example = "524")
    private Integer price;

    @Schema(description = "가격 변화량", example = "-142")
    private Integer priceChange;

    @Schema(description = "변화량 절대값", example = "142")
    private Integer changeAbsolute;

    @Schema(description = "변화 방향", example = "down")
    private String changeDirection;

    @Schema(description = "변화율", example = "+20.3")
    private Float changeRate;
}
