package com.memesphere.domain.notification.service;

import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import com.memesphere.domain.notification.entity.Notification;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import com.memesphere.domain.notification.converter.NotificationConverter;
import com.memesphere.domain.notification.dto.request.NotificationRequest;
import com.memesphere.domain.notification.dto.response.NotificationListResponse;
import com.memesphere.domain.notification.dto.response.NotificationResponse;
import com.memesphere.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinNotificationServiceImpl implements CoinNotificationService {

    private final MemeCoinRepository memeCoinRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationListResponse findNotificationList() {
        List<Notification> notifications = notificationRepository.findAll();
        List<NotificationResponse> notificationResponses = new ArrayList<>();

        for (Notification notification : notifications) {

            MemeCoin memeCoin = memeCoinRepository.findByName(notification.getMemeCoin().getName())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

            NotificationResponse notificationResponse = NotificationConverter.toNotificationCreateResponse(notification, memeCoin);
            notificationResponses.add(notificationResponse);
        }

        NotificationListResponse notificationListResponse = NotificationConverter.toNotificationListResponse(notificationResponses);

        return notificationListResponse;
    }

    // 알림 등록 API
    @Override
    public NotificationResponse addNotification(NotificationRequest notificationRequest) {
        MemeCoin memeCoin = memeCoinRepository.findByName(notificationRequest.getName())
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

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

        MemeCoin memeCoin = memeCoinRepository.findById(notificationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));

        notificationRepository.deleteById(notificationId);

        return null;
    }
}
