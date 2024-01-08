package com.finfellows.domain.product.dto.condition;

import com.finfellows.domain.product.domain.CmaType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CmaSearchCondition {

    @Schema(type = "string", example = "RP, BALHENG, JONGGEUM", description = "CMA 타입입니다.")
    private CmaType cmaType;

}
