package com.memesphere.notification.service;

import com.memesphere.notification.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinNotificationServiceImpl implements CoinNotificationService {

    @Override
    public NotificationDTO.NotificationListResponse findNotificationList() {
        return null;
    }

    @Override
    public NotificationDTO.NoticeForm addNotification(NotificationDTO.NotificationRequest request) {
        return null;
    }

    @Override
    public NotificationDTO.NoticeForm modifyNotification(Long notificationId) {
        return null;
    }

    @Override
    public NotificationDTO.NotificationListResponse removeNotification(Long notificationId) {
        return null;
    }
}
