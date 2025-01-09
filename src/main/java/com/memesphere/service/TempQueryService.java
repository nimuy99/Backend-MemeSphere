package com.memesphere.service;

import com.memesphere.apipayload.code.status.ErrorStatus;
import com.memesphere.apipayload.exception.handler.TempHandler;

public class TempQueryService {
    public void CheckFlag(Integer flag) {
        if (flag == 1)
            throw new TempHandler(ErrorStatus.INTERNAL_SERVER_ERROR);
    }
}
