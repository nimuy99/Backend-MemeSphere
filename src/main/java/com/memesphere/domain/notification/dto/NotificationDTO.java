package com.memesphere.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class NotificationDTO {

    @Getter
    @Builder
    public static class NotificationRequest {
        private String name;
        private String symbol;
        private Integer volatility;
        private Integer st_time;
        private Boolean is_rising;
    }


    @Builder
    @Getter
    public static class NoticeForm {
        private Long notificationId;
        private String name;
        private String symbol;
        private Integer volatility;
        private Integer st_time;
        private Boolean is_rising;
        private Boolean is_on;
    }

    @Builder
    @Getter
    public static class NotificationListResponse {
        private List<NoticeForm> notificationList;
    }
}
