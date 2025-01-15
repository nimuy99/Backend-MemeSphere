package com.memesphere.controller;

import com.memesphere.apipayload.ApiResponse;
import com.memesphere.domain.Collection;
import com.memesphere.service.collectionService.CollectionQueryService;
import com.memesphere.validation.annotation.CheckPage;
import com.sun.security.auth.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.memesphere.dto.CollectionResponseDTO;
import com.memesphere.converter.CollectionConverter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionRestController {
    private final CollectionQueryService collectionService;

    @GetMapping("/")
    @Operation(summary = "사용자의 밈코인 콜렉션 모음 조회 API")
    public ApiResponse<CollectionResponseDTO.CollectionListDTO> getCollectionList (
//            @AuthenticationPrincipal UserPrincipal userPrincipal, // 현재 로그인한 사용자 (아직 구현 x)
            @CheckPage @RequestParam(name = "page") Integer page // 페이지 번호
    ) {
        int pageNumber = page - 1;
//        Long userId = userPrincipal.getId();
        Long userId = 1L;
        Page<Collection> collectionList = collectionService.getCollectionList(userId, pageNumber);
        return ApiResponse.onSuccess(CollectionConverter.toCollectionListDTO(collectionList));
    }

}