package com.memesphere.domain.notification.controller;

import com.memesphere.domain.notification.dto.request.SseSendRequest;
import com.memesphere.domain.notification.service.CoinNotificationService;
import com.memesphere.domain.notification.service.PushNotificationService;
import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.domain.notification.dto.response.NotificationListResponse;
import com.memesphere.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name="푸시 알림", description = "푸시 알림 관련 API")
@RestController
@RequestMapping("/push-notifications")
@RequiredArgsConstructor
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE) //서버가 클라이언트에게 이벤트 스트림을 전송한다는 것을 명시
    @Operation(summary = "알림 전송 API",
            description = """
                    클라이언트와 서버 연결을 시작합니다. \n
                    연결은 1시간 동안 유지됩니다.
                    등록한 알림이 기준 시간 내 변동성에 해당하는 경우 알림을 전송합니다. \n
                    변동성은 직접 계산하지 않고 외부 API에서 받아오는 정보를 기준으로 하고 있습니다.
                    """)
    public SseEmitter subscribe(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                    @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {

        SseEmitter sseEmitter = pushNotificationService.subscribe(customUserDetails.getUser().getId(), lastEventId);
        return sseEmitter;
    }
}
