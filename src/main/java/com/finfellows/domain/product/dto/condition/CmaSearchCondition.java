package com.finfellows.domain.product.dto.condition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CmaSearchCondition {

    @Schema(type = "array", implementation = String.class, example = "[\"RP\", \"BALHAENG\", \"JONGGEUM\"]", description = "CMA 타입입니다.")
    private String[] cmaTypes;

    @Schema(type = "array", implementation = String.class, example = "[\"은행A\", \"은행B\"]", description = "은행 이름입니다.")
    private String[] bankNames;

}
