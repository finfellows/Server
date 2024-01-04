package com.finfellows.domain.product.dto.condition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FinancialProductSearchCondition {

    @Schema(type = "string", example = "020000(1금융권), 030300(저축은행)", description = "은행 종류입니다.")
    private String bankGroupNo;

    @Schema(type = "int", example = "6", description = "금융 상품 기간입니다.")
    private Integer term;

    @Schema(type = "string", example = "누구나 가입", description = "금융 상품 상품 유형입니다.")
    private String type;

}
