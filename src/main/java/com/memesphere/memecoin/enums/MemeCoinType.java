package com.memesphere.memecoin.enums;

import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;

public enum MemeCoinType {
    DOGE("Dogecoin"),
    SHIB("SHIBA INU"),
    TRUMP("OFFICIAL TRUMP"),
    PEPE("Pepe"),
    BONK("Bonk"),
    PENGU("Pudgy Penguins"),
    WIF("Dogwifhat"),
    FLOKI("FLOKI"),
    AIXBT("aixbt by Virtuals"),
    TURBO("Turbo"),
    ORDI("ORDI"),
    PNUT("Peanut the Squirrel");

    private final String name;

    MemeCoinType(String name) {
        this.name = name;
    }

    // method
    public String getName() {
        return name;
    }

    public static String getNameBySymbol(String symbol) {
        for (MemeCoinType type : values()) {
            if (type.name().equalsIgnoreCase(symbol)) {
                return type.getName();
            }
        }
        throw new GeneralException(ErrorStatus.MEMECOIN_NOT_FOUND);
    }
}
