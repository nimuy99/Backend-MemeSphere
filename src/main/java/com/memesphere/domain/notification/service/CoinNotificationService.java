package com.memesphere.domain.notification.service;

import com.memesphere.domain.notification.dto.request.NotificationRequest;
import com.memesphere.domain.notification.dto.response.NotificationListResponse;
import com.memesphere.domain.notification.dto.response.NotificationResponse;

public interface CoinNotificationService {
    NotificationListResponse findNotificationList();
    NotificationResponse addNotification(NotificationRequest notificationRequest);
    String modifyNotification(Long notificationId);
    NotificationListResponse removeNotification(Long notificationId);
}
