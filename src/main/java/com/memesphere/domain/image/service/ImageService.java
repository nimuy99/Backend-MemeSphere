package com.memesphere.domain.image.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.memesphere.domain.image.converter.ImageConverter;
import com.memesphere.domain.image.dto.response.PresignedUrlResponse;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png");

    public PresignedUrlResponse uploadFile(String extension) {

        // 확장자 검사
        validateImageFileExtension(extension);
        String fileName = generateFileName(extension);

        try {
            URL presignedUrl = generatePresignedUrl(fileName, extension); // presigned URL 생성
            String s3BaseUrl = "https://umc-meme.s3.ap-northeast-2.amazonaws.com/";
            String imageUrl = s3BaseUrl + fileName; // 전체 URL 생성

            return ImageConverter.toPresignedUrlDto(presignedUrl, imageUrl);

        }  catch (Exception e) {
            throw new GeneralException(ErrorStatus.FILE_UPLOAD_FAILED);
        }
    }

    //Presigned URL 생성
    public URL generatePresignedUrl(String fileName, String extension) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10; // 10분 후 만료
        expiration.setTime(expTimeMillis);

        String contentType = getContentType(extension);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration)
                        .withContentType(contentType);

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

    }

    //확장자에 따라 content type 결정
    private String getContentType(String extension) {
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            default -> throw new GeneralException(ErrorStatus.INVALID_FILE_EXTENTION);
        };
    }

    // 파일 확장자 유효성 검사
    private void validateImageFileExtension(String extension) {
        if (extension == null || extension.isEmpty() || !ALLOWED_EXTENSIONS.contains(extension)) {
            throw new GeneralException(ErrorStatus.INVALID_FILE_EXTENTION);
        }
    }

    // 파일명 설정
    private String generateFileName(String extension) {
        return UUID.randomUUID() + "." + extension;
    }
}
