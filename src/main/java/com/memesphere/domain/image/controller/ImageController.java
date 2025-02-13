package com.memesphere.domain.image.controller;

import com.memesphere.domain.image.dto.response.PresignedUrlResponse;
import com.memesphere.domain.image.dto.request.ImageExtensionRequest;
import com.memesphere.domain.image.service.ImageService;
import com.memesphere.domain.image.service.ProfileService;
import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/image")
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final ProfileService profileService;

    @PostMapping(value = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "이미지 Presigned URL 발급",
            description = "업로드할 파일의 확장자(jpg, jpeg, png만 가능)를 전송하면 해당 이미지의 Presigned URL을 발급받습니다. \n" +
                    "발급된 Presigned URL로 'PUT' 요청을 보내 실제 이미지를 업로드하면, 성공 시 imageUrl이 활성화됩니다.")

            public ApiResponse<PresignedUrlResponse> uploadFile(@RequestBody ImageExtensionRequest request) {

        String extension = request.getExtension();
        // 확장자에 맞는 파일명 생성 및 Presigned URL 생성
        PresignedUrlResponse url = imageService.uploadFile(extension);

        return ApiResponse.onSuccess(url);
    }

    // 프로필 이미지 조회 Api
    @GetMapping("/profile")
    @Operation(summary = "프로필 이미지 조회 API",
            description = "현재 로그인한 유저의 프로필 이미지를 반환합니다.")
    public ApiResponse<String> getProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // 현재 로그인한 유저 정보에서 프로필 이미지 가져오기
        String profileImage = profileService.getProfileImage(customUserDetails);

        return ApiResponse.onSuccess((profileImage == null || profileImage.isEmpty()) ? "null" : profileImage);
    }

}
