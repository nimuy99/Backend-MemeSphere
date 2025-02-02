package com.memesphere.notification.service;

import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinNotificationServiceImpl implements CoinNotificationService {

    private final MemeRepository memeRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationListResponse findNotificationList() {
        List<Notification> notifications = notificationRepository.findAll();
        List<NotificationResponse> notificationResponses = new ArrayList<>();

        for (Notification notification : notifications) {

            MemeCoin memeCoin = memeRepository.findByName(notification.getMemeCoin().getName())
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
        MemeCoin memeCoin = memeRepository.findByName(notificationRequest.getName())
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND));;

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
