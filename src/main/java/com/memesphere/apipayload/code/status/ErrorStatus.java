package com.memesphere.apipayload.code.status;

import com.memesphere.apipayload.code.BaseCode;
import com.memesphere.apipayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {

    // 에러 응답 예시
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 밈코인 에러
    MEMECOIN_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMECOIN NOT FOUND", "밈코인을 찾을 수 없습니다."),

    // unsupported enum type
    UNSUPPORTED_SORT_TYPE(HttpStatus.BAD_REQUEST,"ENUM400","가능한 SortType은 MKT_CAP, VOLUME_24H, PRICE 입니다."),
    UNSUPPORTED_VIEW_TYPE(HttpStatus.BAD_REQUEST,"ENUM400","가능한 ViewType은 GRID, LIST 입니다."),

    // 밈코인 에러
    MEMECOIN_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMECOIN NOT FOUND", "밈코인을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
