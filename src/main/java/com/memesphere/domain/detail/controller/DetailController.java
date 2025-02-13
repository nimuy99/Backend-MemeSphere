package com.memesphere.domain.detail.controller;

import com.memesphere.domain.detail.dto.response.PriceInfoResponse;
import com.memesphere.domain.detail.service.DetailQueryServiceImpl;
import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.domain.detail.dto.response.DetailGetResponse;
import com.memesphere.domain.detail.service.DetailQueryService;
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

    private final DetailQueryService detailQueryService;

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
                    - "rank": 밈코인 순위(1 ~ 5위까지, 나머지 순위는 null)
                    ```""")
    public ApiResponse<DetailGetResponse> getDetail(@PathVariable("memeId") Long memeId) {

        DetailGetResponse detailGetResponse = detailQueryService.getDetail(memeId);
        return ApiResponse.onSuccess(detailGetResponse);
    }

    @GetMapping("/{memeId}/price-info")
    @Operation(summary = "밈코인 가격 정보 조회 API",
            description = """
                    해당 밈코인의 24시간 기준 가격 정보를 보여줍니다. \n
                    
                    **요청 형식**:
                    ```
                    "memeId": 코인 아이디
                    ```
                    \n
                    **응답 형식**:
                    ```
                    - "coinId": 코인 아이디
                    - "price": 현재가
                    - "priceChange": 가격 변화량
                    - "priceChangeAbsolute": 가격 변화량(절대값)
                    - "priceChangeDirection": 밈코인 상승(up, 0 이상)/하락(down)
                    - "priceChangeRate": 가격 변화율
                    - "weightedAveragePrice": 거래량 가중 평균 가격
                    - "highPrice": 24h 최고가
                    - "lowPrice": 24h 최저가
                    ```""")
    public ApiResponse<PriceInfoResponse> getPriceInfo(@PathVariable("memeId") Long coinId) {

        return ApiResponse.onSuccess(detailQueryService.findPriceInfo(coinId));
    }
}
