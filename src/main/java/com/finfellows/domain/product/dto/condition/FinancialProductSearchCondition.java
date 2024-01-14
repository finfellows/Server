package com.finfellows.domain.product.dto.condition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FinancialProductSearchCondition {

    @Schema(type = "array", implementation = String.class, example = "[\"020000\", \"030300\"]", description = "은행 종류입니다.")
    private String[] bankGroupNos;

    @Schema(type = "array", implementation = Integer.class, example = "[6, 12]", description = "금융 상품 기간입니다.")
    private Integer[] terms;

    @Schema(type = "array", implementation = String.class, example = "[\"누구나 가입\", \"특정 조건\"]", description = "금유 상품 유형입니다.")
    private String[] types;

    @Schema(type = "array", implementation = String.class, example = "[\"은행A\", \"은행B\"]", description = "은행 이름입니다.")
    private String[] bankNames;

    @Schema(type = "string", example = "MAX, DEFAULT", description = "이율 정렬 기준입니다.")
    private String interestRateType;

}
