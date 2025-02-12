package com.memesphere.domain.notification.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {

    // 멀티스레드 환경에서 동시성 문제 방지 위함
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    // 클라이언트와의 SSE 연결 저장
    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByUserId(String UserId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(UserId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithByUserId(String UserId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(UserId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void saveEventCache(String emitterId, Object event) {
        eventCache.put(emitterId, event);
    }
}
