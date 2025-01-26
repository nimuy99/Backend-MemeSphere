package com.memesphere.global.apipayload.exception;

import com.memesphere.global.apipayload.code.BaseCode;
import com.memesphere.global.apipayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseCode code;

    public ReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}