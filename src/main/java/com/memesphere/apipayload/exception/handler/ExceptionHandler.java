package com.memesphere.apipayload.exception.handler;

import com.memesphere.apipayload.code.BaseCode;
import com.memesphere.apipayload.exception.GeneralException;

public class ExceptionHandler extends GeneralException {
    public ExceptionHandler(BaseCode code) {
        super(code);
    }
}
