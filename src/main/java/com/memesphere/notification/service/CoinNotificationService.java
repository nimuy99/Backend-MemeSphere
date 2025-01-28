package com.memesphere.notification.service;

import com.memesphere.notification.dto.request.NotificationRequest;
import com.memesphere.notification.dto.response.NotificationListResponse;
import com.memesphere.notification.dto.response.NotificationResponse;

public interface CoinNotificationService {
    NotificationListResponse findNotificationList();
    NotificationResponse addNotification(NotificationRequest notificationRequest);
    NotificationResponse modifyNotification(Long notificationId);
    NotificationListResponse removeNotification(Long notificationId);
}
