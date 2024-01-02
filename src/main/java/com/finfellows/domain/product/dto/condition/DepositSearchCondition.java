package com.finfellows.domain.product.dto.condition;

import lombok.Data;

@Data
public class DepositSearchCondition {

    private Integer term;
    private String type;
    private String preferentialCondition;

}
