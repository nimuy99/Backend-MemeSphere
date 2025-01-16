package com.memesphere.controller;

import com.memesphere.apipayload.ApiResponse;
import com.memesphere.domain.MemeCoin;
import com.memesphere.dto.SearchResponseDTO;
import com.memesphere.service.searchService.SearchQueryService;
import com.memesphere.converter.SearchConverter;
import com.memesphere.validation.annotation.CheckPage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchRestController {
    private final SearchQueryService searchQueryService;

    @GetMapping("/")
    @Operation(summary = "검색 결과 조회 API", description = "검색어와 페이지 번호를 기준으로 검색 결과를 반환합니다.")
    public ApiResponse<SearchResponseDTO.SearchListDTO> getSearchList(
            @RequestParam(name = "searchWord") String searchWord, // 검색어
            @RequestParam(name = "viewType", defaultValue = "grid") String viewType, // 뷰 타입 (grid 또는 list)
            @CheckPage @RequestParam(name = "page") Integer page // 페이지 번호
    ) {
        int pageNumber = (page == 1) ? 0 : page;
        Page<MemeCoin> searchList = searchQueryService.getSearchList(searchWord, viewType, pageNumber);
        return ApiResponse.onSuccess(SearchConverter.toSearchListDTO(searchList, viewType));
    }
}
