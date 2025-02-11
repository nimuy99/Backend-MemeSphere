package com.memesphere.domain.notification.service;

import com.memesphere.domain.notification.entity.Notification;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface PushNotificationService {
    SseEmitter subscribe(Long userId, String lastEventId);
    void send(Notification notification, Long userId);
}
