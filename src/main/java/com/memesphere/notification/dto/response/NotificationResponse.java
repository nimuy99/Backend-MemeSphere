package com.memesphere.notification.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponse {

    private Long notificationId;
    private String name;
    private String symbol;
    private Integer volatility;
    private Integer stTime;
    private Boolean isRising;
    private Boolean isOn;
}
