package com.memesphere.domain.image.converter;

import com.memesphere.domain.image.dto.response.PresignedUrlResponse;

import java.net.URL;

public class ImageConverter {
    public static PresignedUrlResponse toPresignedUrlDto(URL presignedUrl, String imageUrl) {
        return PresignedUrlResponse.builder()
                .presignedUrl(presignedUrl.toString())
                .imageUrl(imageUrl)
                .build();
    }
}
