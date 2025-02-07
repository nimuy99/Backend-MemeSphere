package com.memesphere.domain.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
public class PresignedUrlResponse {
    @Schema(description = "Presigned URL", example = "https://s3.bucket.com/...") // 예제 추가
    private String presignedUrl;

    @Schema(description = "Image URL", example = "https://umc..jpg")
    private String imageUrl;

}

