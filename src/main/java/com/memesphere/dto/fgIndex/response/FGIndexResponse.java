package com.memesphere.dto.fgIndex.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class FGIndexResponse {
    private LocalDate date;
    private Integer score;
    private String status;
}
