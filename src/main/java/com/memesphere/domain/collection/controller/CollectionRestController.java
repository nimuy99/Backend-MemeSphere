package com.memesphere.domain.collection.controller;

import com.memesphere.domain.collection.converter.CollectionConverter;
import com.memesphere.domain.collection.dto.response.CollectionPageResponse;
import com.memesphere.domain.collection.entity.Collection;
import com.memesphere.domain.collection.service.CollectionQueryService;
import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.global.validation.annotation.CheckPage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

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