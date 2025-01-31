package com.memesphere.domain.dashboard.controller;

import com.memesphere.domain.dashboard.dto.response.DashboardOverviewResponse;
import com.memesphere.domain.dashboard.service.DashboardQueryService;
import com.memesphere.global.apipayload.ApiResponse;
import com.memesphere.domain.dashboard.dto.response.DashboardTrendListResponse;
import com.memesphere.global.validation.annotation.CheckPage;
import com.memesphere.domain.search.dto.response.SearchPageResponse;
import com.memesphere.domain.search.entity.SortType;
import com.memesphere.domain.search.entity.ViewType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
                    - "trendList": 등록된 밈코인의 트렌드 순위 리스트(5위 까지)
                    - "coinId": 밈코인 아이디
                    - "image": 밈코인 이미지
                    - "name": 밈코인 이름
                    - "symbol": 밈코인 심볼
                    - "price": 밈코인 현재가
                    - "priceChange": 가격 변화량
                    - "changeAbsolute": 가격 변화량(절대값)
                    - "changeDirection": 밈코인 상승(up)/하락(down)/유지(= 변화량 0일 때, -) 방향
                    - "changeRate": 가격 변화율
                    ```""")
    public ApiResponse<DashboardTrendListResponse> getTrendList() {
        return ApiResponse.onSuccess(dashboardQueryService.getTrendList());
    }

    @GetMapping("/chart")
    @Operation(summary = "차트 조회 API",
            description = """
                    밈스피어에 등록된 밈코인의 차트 데이터를 보기 방식과 정렬 기준에 따라 보여줍니다. \n

                    **요청 형식**:
                    ```
                    - "viewType": 보기 방식 (GRID / LIST)
                    - "sortType": 정렬 기준 (MKT_CAP / VOLUME_24H / PRICE)
                    - "page": 페이지 번호
                    ```
                    
                    **응답 형식**:
                    ```
                    - "gridItems": 그리드형 응답 리스트
                    - "listItems": 리스트형 응답 리스트
                    - "coinId": 밈코인 아이디 (g/l)
                    - "name": 밈코인 이름 (g/l)
                    - "symbol": 밈코인 심볼 (g/l)
                    - "currentPrice": 밈코인 현재가 (g/l)
                    - "highPrice" : 최고가 (g)
                    - "variation": 가격 변화량 (g)
                    - "lowPrice" : 최저가 (l)
                    - "market_cap": 시가총액 (l)
                    - "volume": 거래량 (l)
                    - "isCollected": 콜랙션 유무(true / false) (g/l)
                    - "listSize": 한 페이지당 보여지는 코인 정보 수(그리드 - 9 / 리스트 - 20)
                    - "totalPage": 볼 수 있는 페이지 개수
                    - "totalElements": 총 코인 정보 수
                    - "isFirst": 첫 페이지인지(true) / 아닌지(false)
                    - "isLast": 마지막 페이지인지(true) / 아닌지(false)
                    ```""")
    public ApiResponse<SearchPageResponse> getChartList(// TODO: userID 변경 -> 로그인한 유저
                                                        @RequestParam(name = "viewType", defaultValue = "GRID") ViewType viewType,
                                                        @RequestParam(name = "sortType", defaultValue = "MKT_CAP") SortType sortType,
                                                        @CheckPage @RequestParam(name = "page") Integer page) {
        Integer pageNumber = page - 1;
        // TODO: userID 변경 -> 로그인한 유저
//        Long userId = user.getId();
        Long userId = 1L;

        return ApiResponse.onSuccess(dashboardQueryService.getChartPage(userId, viewType, sortType, pageNumber));
    }
}
