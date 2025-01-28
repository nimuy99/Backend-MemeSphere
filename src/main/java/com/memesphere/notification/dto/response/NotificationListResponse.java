package com.memesphere.notification.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NotificationListResponse {

    private List<NotificationResponse> notificationList;
}
