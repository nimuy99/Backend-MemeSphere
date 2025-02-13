package com.memesphere.domain.notification.controller;


import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.domain.notification.dto.request.NotificationRequest;
import com.memesphere.domain.notification.dto.response.NotificationListResponse;
import com.memesphere.domain.notification.dto.response.NotificationResponse;
import com.memesphere.domain.notification.service.CoinNotificationService;
import com.memesphere.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name="알림", description = "알림 관련 API")
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class CoinNotificationController {

    final private CoinNotificationService coinNotificationService;

    @GetMapping("/list")
    @Operation(summary = "등록한 알림 리스트 조회 API",
            description = """
                    사용자가 등록해 놓은 알림 리스트를 보여줍니다. \n
                    
                    **요청 형식**: ```없음```
                    
                    **응답 형식**:
                    ```
                    - "notificationList": 사용자가 등록한 알림 리스트
                    - "notificationId": 알림 아이디
                    - "name": 밈코인 이름
                    - "symbol": 밈코인 심볼
                    - "volatility": 변동성
                    - "st_time": 기준 시간
                    - "is_rising": 상승(true) 또는 하락(false)
                    - "is_on": 알림 on(true) 또는 off(false)
                    ```""")
    public ApiResponse<NotificationListResponse> getNotificationList() {
        return ApiResponse.onSuccess(coinNotificationService.findNotificationList());
    }

    @PostMapping("/enroll")
    @Operation(summary = "알림 등록 API",
            description = """
                    사용자가 직접 필드를 입력하여 알림을 등록합니다. \n
                    
                    **요청 형식**:
                    ```
                    - "name": 밈코인 이름(둘 중 하나만 작성)
                    - "symbol": 밈코인 심볼(둘 중 하나만 작성)
                    - "volatility": 변동성
                    - "st_time": 기준 시간
                    - "is_rising": 상승(true) 또는 하락(false)
                    ```
                    
                    **응답 형식**:
                    ```
                    - "notificationId": 새로 등록된 알림의 밈코인 아이디
                    - "name": 새로 등록된 알림의 밈코인 아이디
                    - "symbol": 새로 등록된 알림의 밈코인 아이디
                    - "volatility": 변동성
                    - "st_time": 기준 시간
                    - "is_rising": 상승(true) 또는 하락(false)
                    - "is_on": 알림 on(true) 또는 off(false)
                    ```""")
    public ApiResponse<NotificationResponse> postNotification(@RequestBody NotificationRequest request,
                                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ApiResponse.onSuccess(coinNotificationService.addNotification(request, customUserDetails.getUser()));
    }

    @PatchMapping("/{notification-id}")
    @Operation(summary = "등록한 알림 상태 수정 API",
            description = """
                    사용자가 등록한 알림 id를 입력하면 해당 알림의 상태(is_on)를 변경합니다. \n
                    
                    **요청 형식**:
                    ```
                    - "notification-id": 알림 아이디
                    ```
                    
                    **응답 형식**:
                    ```
                    알림 등록 API 응답 형식과 동일
                    ```""")
    public ApiResponse<String> updateNotificationStatus(@PathVariable("notification-id") Long id) {
        return ApiResponse.onSuccess(coinNotificationService.modifyNotification(id));
    }

    @DeleteMapping("/{notification-id}")
    @Operation(summary = "등록한 알림 삭제 API",
            description = """
                    사용자가 등록한 알림 id를 입력하면 해당 알림을 삭제합니다. \n
                    
                    **요청 형식**:
                    ```
                    - "notification-id": 알림 아이디
                    ```
                    
                    **응답 형식**:
                    ```
                    등록한 알림 조회 API 응답 형식과 동일
                    ```""")
    public ApiResponse<NotificationListResponse> deleteNotification(@PathVariable("notification-id") Long id) {
        coinNotificationService.removeNotification(id);
        return ApiResponse.onSuccess(coinNotificationService.findNotificationList());
    }
}
