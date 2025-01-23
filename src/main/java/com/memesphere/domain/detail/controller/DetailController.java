package com.memesphere.domain.detail.controller;

import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.domain.detail.dto.response.DetailGetResponse;
import com.memesphere.domain.detail.service.DetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "밈코인 디테일 페이지", description = "세부 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/detail")
public class DetailController {

    private final DetailService detailService;

    @GetMapping("/{memeId}")
    @Operation(summary = "밈코인 세부 정보 조회 API",
            description = """
                    해당 밈코인의 세부 정보를 보여줍니다. \n
                    
                    **요청 형식**: ```없음```
                    
                    **응답 형식**:
                    ```
                    - "id": 밈코인 아이디(번호)
                    - "name": 밈코인 이름
                    - "symbol": 심볼
                    - "description": 밈코인 상세 설명
                    - "image": 밈코인 이미지
                    - "keywords": 밈코인 키워드 (리스트 형식)
                    - "collectionActive": 컬렉션 유무 (저장 유무)
                    ```""")
    public ApiResponse<DetailGetResponse> getDetail(@PathVariable("memeId") Long memeId) {

        DetailGetResponse detailGetResponse = detailService.getDetail(memeId);
        return ApiResponse.onSuccess(detailGetResponse);
    }
}
