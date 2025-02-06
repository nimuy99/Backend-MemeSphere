package com.memesphere.domain.notification.controller;

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
    public ApiResponse<SseEmitter> subscribe(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                    @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {

        SseEmitter sseEmitter = pushNotificationService.subscribe(customUserDetails.getUser().getId(), lastEventId);
        return ApiResponse.onSuccess(sseEmitter);
    }

    @GetMapping
    @Operation(summary = "푸시 알림 조회 API",
            description = """
                    푸시된 알림 리스트를 보여줍니다. \n
                    푸시 알림을 db에 저장할 필요가 있을까요? \n
                    읽음 여부를 확인해야 한다면 푸시 알림 테이블을 만드는게 맞을까요?  \n
                    푸시 알림 기능이 어떻게 작동되는지 몰라서 컨트롤러만 두었습니다.  \n
                    응답 형식은 일단 무시해주세요.""")
    public ApiResponse<NotificationListResponse> getPushList() {
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{notification-id}")
    @Operation(summary = "푸시 알림 삭제? 확인? API",
            description = """
                    푸시된 알림 리스트를 삭제? 확인?합니다. \n
                    이 기능은 푸시 알림 기능을 더 정의한 후에 수정해야 할 듯 보입니다. 
                    """)
    public ApiResponse<NotificationListResponse> deletePushNotification() {
        return ApiResponse.onSuccess(null);
    }
}
