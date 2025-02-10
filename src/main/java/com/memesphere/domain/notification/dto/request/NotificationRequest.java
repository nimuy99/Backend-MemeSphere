package com.memesphere.domain.notification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationRequest {

    @Schema(description = "코인 이름", example = "Dogecoin")
    private String name;

    @Schema(description = "코인 심볼", example = "DOGE/USDT")
    private String symbol;

    @Schema(description = "변동성", example = "5")
    private Integer volatility;

    @Schema(description = "기준 시간", example = "10")
    private Integer stTime;

    @Schema(description = "상승 또는 하락", example = "True")
    private Boolean isRising;
}
