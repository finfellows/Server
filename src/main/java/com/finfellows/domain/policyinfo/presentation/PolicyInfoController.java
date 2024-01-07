package com.finfellows.domain.policyinfo.presentation;

import com.finfellows.global.payload.ErrorResponse;
import com.finfellows.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Policy Information", description = "Policy Information API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/policy-info")
public class PolicyInfoController {

//    @Operation(summary = "정책 리스트 조회", description = "정책 리스트를 조건에 따라 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "정책 리스트 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))}),
//            @ApiResponse(responseCode = "400", description = "정책 리스트 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
//    })
//    @GetMapping("/bank")
//    public ResponseCustom<List<String>> findBanks(
//            @Parameter(description = "조회 할 페이지와 페이지 크기를 입력해주세요") Pageable pageable
//    ) {
//        return ResponseCustom.OK(financialProductServiceImpl.findBanks(bankGroupNo));
//    }

}
