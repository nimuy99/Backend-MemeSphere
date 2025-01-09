package com.memesphere.apipayload.exception.handler;

import com.memesphere.apipayload.code.BaseCode;
import com.memesphere.apipayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseCode errorCode) {
        super(errorCode);
    }
}
