package com.finfellows.domain.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CmaType {

    RP("RP형"),
    BALHAENG("발행어음형"),
    JONGGEUM("종금형");

    private final String value;

    public static CmaType fromString(String text) {
        for (CmaType cmaType : CmaType.values()) {
            if (cmaType.name().equalsIgnoreCase(text)) {
                return cmaType;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

}
