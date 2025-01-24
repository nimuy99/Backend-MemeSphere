package com.memesphere.notification.service;

import com.memesphere.notification.dto.NotificationDTO;

public interface CoinNotificationService {
    NotificationDTO.NotificationListResponse findNotificationList();
    NotificationDTO.NoticeForm addNotification(NotificationDTO.NotificationRequest notificationRequest);
    NotificationDTO.NoticeForm modifyNotification(Long notificationId);
    NotificationDTO.NotificationListResponse removeNotification(Long notificationId);
}
