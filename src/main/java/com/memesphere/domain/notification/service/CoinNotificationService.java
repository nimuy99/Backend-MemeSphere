package com.memesphere.domain.notification.service;

import com.memesphere.domain.notification.dto.request.NotificationRequest;
import com.memesphere.domain.notification.dto.response.NotificationListResponse;
import com.memesphere.domain.notification.dto.response.NotificationResponse;
import com.memesphere.domain.user.entity.User;

public interface CoinNotificationService {
    NotificationListResponse findNotificationList();
    NotificationResponse addNotification(NotificationRequest notificationRequest, User user);
    String modifyNotification(Long notificationId);
    NotificationListResponse removeNotification(Long notificationId);
}
