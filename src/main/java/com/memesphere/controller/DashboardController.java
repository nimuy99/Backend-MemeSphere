package com.memesphere.controller;

import com.memesphere.apipayload.ApiResponse;
import com.memesphere.dto.DashboardDTO;
import com.memesphere.service.dashboard.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="대시보드", description = "대시보드 관련  API")
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/trend")
    @Operation(summary = "트렌드 조회 API",
            description = """
                    밈스피어에 등록된 밈코인의 총 거래량 및 총 개수, \n
                    그리고 24시간 내 거래량이 가장 많은 밈코인을 5위까지 보여줍니다. \n
                    
                    **요청 형식**: ```없음```
                    
                    **응답 형식**:
                    ```
                    - "totalVolume": 등록된 밈코인의 총 거래량
                    - "totalCoin": 등록된 밈코인의 총 개수
                    - "trendList": 등록된 밈코인의 트렌드 순위 리스트(5위 까지)
                    - "coinId": 밈코인 아이디
                    - "image": 밈코인 이미지
                    - "name": 밈코인 이름
                    - "symbol": 밈코인 심볼
                    - "price": 밈코인 현재가
                    - "direction": 밈코인 상승(up)/하락(down) 방향
                    - "priceChange": 가격 변화량
                    - "changeRate": 가격 변화율
                    ```""")
    public ApiResponse<DashboardDTO.TrendResponse> getTrendList() {
        return ApiResponse.onSuccess(dashboardService.findTrendList());
    }

    @GetMapping("/related-search")
    @Operation(summary = "연관 검색어 조회 API",
            description = """
                    24시간 기준 밈코인과 관련된 연관 검색어 2개를 수치와 함께 보여줍니다. \n
                    
                    **요청 형식**: ```없음```
                    
                    **응답 형식**:
                    ```
                    - "label1": 첫 번째 연관 검색어의 이름,
                    - "searchVolumeList1": 첫 번째 연관 검색어의 시간에 따른 검색량 수 리스트
                    - "label2": 두 번째 연관 검색어의 이름
                    - "searchVolumeList2": 두 번째 연관 검색어의 시간에 따른 검색량 수 리스트
                    - "time": 시간
                    - "volume": 검색량
                    ```""")
    public ApiResponse<DashboardDTO.RelatedSearchResponse> getRelatedSearch() {
        return ApiResponse.onSuccess(dashboardService.findRelatedSearch());
    }
}
