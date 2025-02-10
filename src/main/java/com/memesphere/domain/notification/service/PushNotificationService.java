package com.memesphere.domain.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface PushNotificationService {
    SseEmitter subscribe(Long userId, String lastEventId);
    void send(Long userId);
}
