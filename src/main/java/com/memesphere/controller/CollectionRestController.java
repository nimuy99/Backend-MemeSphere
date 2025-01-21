package com.memesphere.controller;

import com.memesphere.apipayload.ApiResponse;
import com.memesphere.domain.Collection;
import com.memesphere.dto.response.CollectionPageResponse;
import com.memesphere.service.collectionService.CollectionQueryService;
import com.memesphere.validation.annotation.CheckPage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.memesphere.converter.CollectionConverter;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/collection")
public class CollectionRestController {
    private final CollectionQueryService collectionQueryService;

    @GetMapping("/collection")
    @Operation(summary = "사용자의 밈코인 콜렉션 모음 조회 API")
    public ApiResponse<CollectionPageResponse> getCollectionList (
//            @AuthenticationPrincipal User user, // 현재 로그인한 사용자 (아직 구현 x)
            @CheckPage @RequestParam(name = "page") Integer page // 페이지 번호
    ) {
        Integer pageNumber = page - 1;
//        Long userId = user.getId();
        Long userId = 1L;

        Page<Collection> collectionPage = collectionQueryService.getCollectionPage(userId, pageNumber);
        return ApiResponse.onSuccess(CollectionConverter.toCollectionPageDTO(collectionPage));
    }

}