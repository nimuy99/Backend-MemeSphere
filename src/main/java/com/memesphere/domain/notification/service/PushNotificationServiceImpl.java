package com.memesphere.domain.notification.service;

import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.notification.converter.NotificationConverter;
import com.memesphere.domain.notification.entity.Notification;
import com.memesphere.domain.notification.repository.EmitterRepository;
import com.memesphere.domain.notification.repository.NotificationRepository;
import com.memesphere.domain.user.entity.User;
import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PushNotificationServiceImpl implements PushNotificationService {

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    // 연결 지속 시간 설정 : 한시간
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    @Override
    public SseEmitter subscribe(Long UserId, String lastEventId) {

        // 고유한 아이디 생성
        String emitterId = UserId + "_" + System.currentTimeMillis(); // 사용자 id + 현재 시간을 밀리초 단위의 long값
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // 클라이언트가 SSE 연결을 종료하면 실행됨
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));

        // 지정된 시간이 지나거나 클라이언트가 요청을 안하면 실행됨
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 최초 연결 더미데이터가 없으면 503 에러가 나므로 더미 데이터 생성
        sendToClient(emitter, emitterId, "EventStream Created. [userId=" + UserId + "]");

        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByUserId(emitterId);
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }
        return emitter;
    }

    @Override
    public void send(Long userId) {

        List<Notification> notifications = notificationRepository.findAllByUserId(userId); // 사용자가 등록한 알림 전부 가져오기

        if (notifications.isEmpty()) {
            return; // 저장된 알림이 없는 경우 아무것도 반환하지 않음
        }

        // 변동성을 초과하는 알림 필터링
        List<Notification> filteredNotifications = notifications.stream()
                .filter(notification -> isVolatilityExceeded(notification))
                .collect(Collectors.toList());

        if (filteredNotifications.isEmpty()) {
            return; // 기준을 충족하는 변동성이 없으면 전송하지 않음
        }

        // 실시간 알림 전송
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByUserId(String.valueOf(userId));

        sseEmitters.forEach((key, emitter) -> {
            filteredNotifications.forEach(notification -> {
                sendToClient(emitter, key, "변동성 초과 알림");
            });
        });
    }

    private void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
            throw new GeneralException(ErrorStatus.CANNOT_PUSH_NOTIFICATION);
        }
    }

    private boolean isVolatilityExceeded(Notification notification) {
        MemeCoin memeCoin = notification.getMemeCoin();
        if (memeCoin == null) {
            throw new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND);
        }

        List<ChartData> chartDataList = memeCoin.getChartDataList();
        if (chartDataList == null || chartDataList.isEmpty()) {
            throw new GeneralException(ErrorStatus.CANNOT_LOAD_CHARTDATA);
        }

        // 최신 가격 가져오기
        Optional<ChartData> latestDataOpt = chartDataList.stream()
                .max(Comparator.comparing(ChartData::getPrice));

        if (latestDataOpt.isEmpty()) {
            throw new GeneralException(ErrorStatus.CANNOT_LOAD_CHARTDATA);  // 최신 데이터가 존재하지 않을 경우
        }

        BigDecimal latestPrice = latestDataOpt.get().getPrice();

        // 기준 시간 내 가장 오래된 가격 가져오기
        Optional<ChartData> oldestDataOpt = chartDataList.stream()
                .filter(data -> data.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(notification.getStTime())))
                .min(Comparator.comparing(ChartData::getPrice));

        if (oldestDataOpt.isEmpty()) {
            throw new GeneralException(ErrorStatus.CANNOT_LOAD_CHARTDATA);
        }

        BigDecimal oldestPrice = oldestDataOpt.get().getPrice();

        if (oldestPrice == null || latestPrice == null) {
            throw new GeneralException(ErrorStatus.CANNOT_CHECK_VOLATILITY);
        }

        // 변동성 계산
        BigDecimal priceDiff = latestPrice.subtract(oldestPrice);
        BigDecimal volatility = priceDiff
                .divide(oldestPrice, 4, BigDecimal.ROUND_HALF_UP) // 나눗셈 수행(소수점 4자리 반올림)
                .multiply(new BigDecimal("100")); // 백분율 변환
        boolean isIncrease = volatility.compareTo(BigDecimal.ZERO) > 0; // 상승 여부 확인 (True: 상승, False: 하락)

        return volatility.abs().intValue() > notification.getVolatility();
//        if (volatility.abs() > notification.getVolatility()) {
//            if (notification.getIsRising() & isIncrease) {
//
//            } else if (!(notification.getIsRising()) & isIncrease)) {
//
//            }
//        }
    }

}
