package com.memesphere.controller;

import com.memesphere.apipayload.ApiResponse;
import com.memesphere.converter.TempConverter;
import com.memesphere.service.TempQueryService;
import com.memesphere.dto.TempResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test")
    public ApiResponse<String> testAPI(){
        return ApiResponse.onSuccess("응답 형식 test");
    }

    @GetMapping("/exception")
    public ApiResponse<TempResponse.TempExceptionDTO> exceptionAPI(@RequestParam Integer flag){
        TempQueryService test = new TempQueryService();
        test.CheckFlag(flag);
        return ApiResponse.onSuccess(TempConverter.toTempExceptionDTO(flag));
    }
}
