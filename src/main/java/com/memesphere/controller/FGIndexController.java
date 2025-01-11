package com.memesphere.controller;


import com.memesphere.domain.FGIndex;
import com.memesphere.dto.fgIndex.response.FGIndexResponse;
import com.memesphere.service.FGIndexService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "공포탐욕지수", description = "공포탐욕지수 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/fgindex")
public class FGIndexController {

    private final FGIndexService fgIndexService;

    //공포탐욕지수를 조회하는 GET 요청
    @GetMapping("/{date}")
    public ResponseEntity<FGIndexResponse> getFearGreedIndex(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        FGIndex fgIndex = fgIndexService.getIndexByDate(date);
        if (fgIndex == null) {
            return ResponseEntity.notFound().build();
        }
        FGIndexResponse response = new FGIndexResponse(fgIndex.getDate(), fgIndex.getScore(), fgIndex.getStatus());
        return ResponseEntity.ok(response);
    }
}
