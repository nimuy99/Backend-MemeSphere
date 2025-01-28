package com.memesphere.notification.converter;

import com.memesphere.memecoin.domain.MemeCoin;
import com.memesphere.notification.domain.Notification;
import com.memesphere.notification.dto.request.NotificationRequest;
import com.memesphere.notification.dto.response.NotificationResponse;

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
}
