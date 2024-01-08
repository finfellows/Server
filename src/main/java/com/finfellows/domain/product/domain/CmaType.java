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

}
