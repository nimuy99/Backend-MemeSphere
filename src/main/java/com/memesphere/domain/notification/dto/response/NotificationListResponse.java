package com.memesphere.domain.notification.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NotificationListResponse {

    private List<NotificationResponse> notificationList;
}
