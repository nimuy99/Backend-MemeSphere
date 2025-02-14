package com.memesphere.domain.naver.controller;


import com.memesphere.domain.naver.dto.request.SearchRequest;
import com.memesphere.domain.naver.dto.response.SearchResponse;
import com.memesphere.domain.naver.service.SearchTrendService;
import com.memesphere.global.apipayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;


@RestController
@RequestMapping("/naver")
@RequiredArgsConstructor
public class SearchTrendController {

    private final SearchTrendService searchTrendService;

    @ResponseBody
    @PostMapping("/trends")
    @Operation(
            summary = "네이버 검색어 트렌드 조회 API",
            description = """
                해당 키워드에 대한 검색량 비율을 제공합니다.
                groupName은 분류 목적이고, 검색 트렌드는 keywords 기준으로 계산됩니다.
                아래 예제와 같이 그룹으로 묶어서 한번에 하나 이상의 요청을 보낼 수 있습니다.
                - `timeUnit` 옵션: `date` (일간) / `week` (주간) / `month` (월간)
                """
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "검색 트렌드 요청 데이터",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "트렌드 조회 예제",
                            summary = "검색 트렌드 조회 요청 예제",
                            value = """
                {
                  "startDate": "2025-02-07",
                  "endDate": "2025-02-13",
                  "timeUnit": "date",
                  "keywordGroups": [
                    {
                      "groupName": "밈코인",
                      "keywords": ["도지코인"]
                    },
                    {
                      "groupName": "밈코인",
                      "keywords": ["시바이누"]
                    }
                  ]
                }
                """
                    )
            )
    )
    public ApiResponse<SearchResponse> getSearchTrends(
            @RequestBody SearchRequest searchRequest
    ) {
        SearchResponse response = searchTrendService.getSearchTrends(searchRequest);
        return ApiResponse.onSuccess(response);
    }
}

