package com.memesphere.notification.service;

import com.memesphere.memecoin.domain.MemeCoin;
import com.memesphere.memecoin.repository.MemeRepository;
import com.memesphere.notification.converter.NotificationConverter;
import com.memesphere.notification.domain.Notification;
import com.memesphere.notification.dto.request.NotificationRequest;
import com.memesphere.notification.dto.response.NotificationListResponse;
import com.memesphere.notification.dto.response.NotificationResponse;
import com.memesphere.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinNotificationServiceImpl implements CoinNotificationService {

    MemeRepository memeRepository;
    NotificationRepository notificationRepository;

    @Override
    public NotificationListResponse findNotificationList() {
        return null;
    }

    // 알림 등록 API
    @Override
    public NotificationResponse addNotification(NotificationRequest notificationRequest) {
        MemeCoin memeCoin = memeRepository.findByName(notificationRequest.getName());

        Notification notification = NotificationConverter.toNotification(notificationRequest, memeCoin);
        notificationRepository.save(notification);

        NotificationResponse notificationResponse = NotificationConverter.toNotificationCreateResponse(notification, memeCoin);

        return notificationResponse;
    }

    @Override
    public NotificationResponse modifyNotification(Long notificationId) {
        return null;
    }

    @Override
    public NotificationListResponse removeNotification(Long notificationId) {
        return null;
    }
}
