package com.memesphere.notification.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationRequest {

    private String name;
    private String symbol;
    private Integer volatility;
    private Integer stTime;
    private Boolean isRising;
}
