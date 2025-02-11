package com.memesphere.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.yaml.snakeyaml.emitter.Emitter;

import java.util.Map;

public interface EmitterRepository {

    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    void deleteById(String emitterId);
    Map<String, SseEmitter> findAllEmitterStartWithByUserId(String UserId);
    Map<String, Object> findAllEventCacheStartWithByUserId(String UserId);
    void saveEventCache(String emitterId, Object event);
}
