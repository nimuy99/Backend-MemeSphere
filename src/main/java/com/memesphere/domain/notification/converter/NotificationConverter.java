package com.memesphere.domain.notification.converter;

import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.notification.dto.request.NotificationRequest;
import com.memesphere.domain.notification.dto.response.NotificationListResponse;
import com.memesphere.domain.notification.dto.response.NotificationResponse;
import com.memesphere.domain.notification.entity.Notification;

import java.util.List;

public class NotificationConverter {

    public static Notification toNotification(NotificationRequest notificationRequest, MemeCoin memeCoin) {
        return Notification.builder()
                .memeCoin(memeCoin)
                .volatility(notificationRequest.getVolatility())
                .stTime(notificationRequest.getStTime())
                .isRising(notificationRequest.getIsRising())
                .build();
    }

    public static NotificationResponse toNotificationCreateResponse(Notification notification, MemeCoin memeCoin) {
        return NotificationResponse.builder()
                .notificationId(notification.getId())
                .name(memeCoin.getName())
                .symbol(memeCoin.getSymbol())
                .volatility(notification.getVolatility())
                .stTime(notification.getStTime())
                .isRising(notification.getIsRising())
                .build();
    }

    public static NotificationListResponse toNotificationListResponse(List<NotificationResponse> notificationResponses) {
        return NotificationListResponse.builder()
                .notificationList(notificationResponses)
                .build();
    }
}
