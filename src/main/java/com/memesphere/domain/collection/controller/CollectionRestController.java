package com.memesphere.domain.collection.controller;

import com.memesphere.domain.collection.service.CollectionCommandService;
import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.domain.collection.entity.Collection;
import com.memesphere.domain.collection.dto.response.CollectionPageResponse;
import com.memesphere.domain.collection.service.CollectionQueryService;
import com.memesphere.global.jwt.TokenProvider;
import com.memesphere.global.validation.annotation.CheckPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.memesphere.domain.collection.converter.CollectionConverter;

@Tag(name="콜렉션", description = "콜렉션 관련  API")
@RestController
@RequiredArgsConstructor
//@RequestMapping("/collection")
public class CollectionRestController {
    private final CollectionQueryService collectionQueryService;
    private final CollectionCommandService collectionCommandService;
    private final TokenProvider tokenProvider;

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

    @PostMapping("/collection/{coinId}")
    @Operation(summary = "밈코인 콜렉션 등록 API",
                description = "코인 Id를 입력하면 사용자의 콜렉션에 등록")
    public ApiResponse<String> postCollectCoin (HttpServletRequest request, @PathVariable Long coinId) {

        String token = request.getHeader("Authorization").substring(7);
        String email = tokenProvider.getLoginId(token);

        return ApiResponse.onSuccess(collectionCommandService.addCollectCoin(email, coinId));
    }

    @DeleteMapping("/collection/{coinId}")
    @Operation(summary = "밈코인 콜렉션 삭제 API",
            description = "코인 Id를 입력하면 사용자의 콜렉션에서 삭제")
    public ApiResponse<String> deleteCollectCoin (HttpServletRequest request, @PathVariable Long coinId) {

        String token = request.getHeader("Authorization").substring(7);
        String email = tokenProvider.getLoginId(token);

        return ApiResponse.onSuccess(collectionCommandService.removeCollectCoin(email, coinId));
    }

}