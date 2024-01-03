package com.finfellows.domain.product.dto.condition;

import lombok.Data;

@Data
public class FinancialProductSearchCondition {

    private Integer term;
    private String type;
    private String preferentialCondition;

}
