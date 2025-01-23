package com.memesphere.dashboard.controller;

import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.dashboard.dto.response.DashboardOverviewResponse;
import com.memesphere.dashboard.dto.response.DashboardTrendListResponse;
import com.memesphere.dashboard.service.DashboardQueryService;
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
    private final DashboardQueryService dashboardQueryService;

    @GetMapping("/overview")
    @Operation(summary = "밈코인 총 거래량 및 총 개수 조회 API",
            description = """
                    밈스피어에 등록된 밈코인의 총 거래량 및 총 개수를 보여줍니다. \n
                    
                    **요청 형식**: ```없음```
                    
                    **응답 형식**:
                    ```
                    - "totalVolume": 등록된 밈코인의 총 거래량
                    - "totalCoin": 등록된 밈코인의 총 개수
                    ```""")
    public ApiResponse<DashboardOverviewResponse> getOverview() {
        return ApiResponse.onSuccess(dashboardQueryService.getOverview());
    }

    @GetMapping("/trend")
    @Operation(summary = "트렌드 조회 API",
            description = """
                    밈스피어에 등록된 24시간 내 거래량이 가장 많은 밈코인을 5위까지 보여줍니다. \n
                    
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
                    - "priceChange": 가격 변화량
                    - "changeAbsolute": 가격 변화량(절대값)
                    - "changeDirection": 밈코인 상승(up)/하락(down) 방향
                    - "changeRate": 가격 변화율
                    ```""")
    public ApiResponse<DashboardTrendListResponse> getTrendList() {
        return ApiResponse.onSuccess(dashboardQueryService.getTrendList());
    }

    @GetMapping("/chart")
    @Operation(summary = "차트 조회 API",
            description = """
                    밈스피어에 등록된 밈코인의 차트 데이터를 보기 방식과 정렬 기준에 따라 보여줍니다. \n
                    
                    **요청 형식**: ```
                    - "show" : 보기 방식
                    - "sort" : 정렬 방식
                    ```
                    
                    **응답 형식**:
                    ```
                    ```""")
    public ApiResponse<DashboardTrendListResponse> getChartList() {
        return ApiResponse.onSuccess(null);
    }
}
