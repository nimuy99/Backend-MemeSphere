package com.memesphere.domain.notification.service;

import com.memesphere.domain.notification.dto.NotificationDTO;

public interface CoinNotificationService {
    NotificationDTO.NotificationListResponse findNotificationList();
    NotificationDTO.NoticeForm addNotification(NotificationDTO.NotificationRequest notificationRequest);
    NotificationDTO.NoticeForm modifyNotification(Long notificationId);
    NotificationDTO.NotificationListResponse removeNotification(Long notificationId);
}
