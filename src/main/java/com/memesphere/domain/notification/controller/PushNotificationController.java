package com.memesphere.notification.controller;

import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.notification.dto.response.NotificationListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="푸시 알림", description = "푸시 알림 관련 API")
@RestController
@RequestMapping("/push-notifications")
@RequiredArgsConstructor
public class PushNotificationController {
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
