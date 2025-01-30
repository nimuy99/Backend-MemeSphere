package com.memesphere.notification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponse {

    @Schema(description = "알림 아이디", example = "1")
    private Long notificationId;

    @Schema(description = "코인 이름", example = "DOGE")
    private String name;

    @Schema(description = "코인 심볼", example = "DOGE/USDT")
    private String symbol;

    @Schema(description = "변동성", example = "5")
    private Integer volatility;

    @Schema(description = "기준 시간", example = "10")
    private Integer stTime;

    @Schema(description = "상승 또는 하락", example = "True")
    private Boolean isRising;

    @Schema(description = "알림 켜기/끄기", example = "True")
    private Boolean isOn;
}
