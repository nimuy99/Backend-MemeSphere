package com.memesphere.domain.notification.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SseSendRequest {

    private String eventName;

    private String data;
}
