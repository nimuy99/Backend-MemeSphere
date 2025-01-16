package com.memesphere.converter;

import com.memesphere.dto.response.TempResponse;

public class TempConverter {
    public static TempResponse.TempExceptionDTO toTempExceptionDTO(Integer flag){
        return TempResponse.TempExceptionDTO.builder()
                .flag(flag)
                .build();
    }
}
