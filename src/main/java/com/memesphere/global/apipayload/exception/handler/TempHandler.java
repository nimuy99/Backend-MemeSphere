package com.memesphere.global.apipayload.exception.handler;

import com.memesphere.global.apipayload.code.BaseCode;
import com.memesphere.global.apipayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseCode errorCode) {
        super(errorCode);
    }
}
