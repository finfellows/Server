package com.finfellows.domain.product.presentation;

import com.finfellows.domain.product.application.FinancialProductServiceImpl;
import com.finfellows.domain.product.dto.condition.CmaSearchCondition;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.request.BankUploadReq;
import com.finfellows.domain.product.dto.response.*;
import com.finfellows.global.config.security.token.CurrentUser;
import com.finfellows.global.config.security.token.UserPrincipal;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.http.Multipart;

import java.io.IOException;
import java.util.List;

@Tag(name = "FinancialProducts", description = "FinancialProducts API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/financial-products")
public class FinancialProductController {

    private final FinancialProductServiceImpl financialProductServiceImpl;

    @Operation(summary = "은행 리스트 조회", description = "은행 리스트를 조건에 따라 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "은행 리스트 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))}),
            @ApiResponse(responseCode = "400", description = "은행 리스트 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/bank")
    public ResponseCustom<List<SearchBankRes>> findBanks(
            @Parameter(description = "1금융권(020000), 저축은행(030300)", required = true) @RequestParam String[] bankGroupNo
    ) {
        return ResponseCustom.OK(financialProductServiceImpl.findBanks(bankGroupNo));
    }

    @Operation(summary = "예금 정보 조회", description = "예금 정보를 조건에 따라 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예금 정보 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SearchFinancialProductRes.class)))}),
            @ApiResponse(responseCode = "400", description = "예금 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/deposit")
    public ResponseCustom<Page<SearchFinancialProductRes>> findDepositProducts(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @ModelAttribute FinancialProductSearchCondition financialProductSearchCondition,
            @Parameter(description = "조회 할 페이지와 페이지 크기를 입력해주세요") Pageable pageable
    ) {
        return ResponseCustom.OK(financialProductServiceImpl.findDepositProducts(userPrincipal, financialProductSearchCondition, pageable));
    }

    @Operation(summary = "적금 정보 조회", description = "적금 정보를 조건에 따라 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "적금 정보 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SearchFinancialProductRes.class)))}),
            @ApiResponse(responseCode = "400", description = "적금 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/saving")
    public ResponseCustom<Page<SearchFinancialProductRes>> findSavingProducts(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @ModelAttribute FinancialProductSearchCondition financialProductSearchCondition,
            @Parameter(description = "조회 할 페이지와 페이지 크기를 입력해주세요") Pageable pageable
    ) {
        return ResponseCustom.OK(financialProductServiceImpl.findSavingProducts(userPrincipal, financialProductSearchCondition, pageable));
    }

    @Operation(summary = "예금 상세 정보 조회", description = "예금 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예금 상세 정보 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DepositDetailRes.class))}),
            @ApiResponse(responseCode = "400", description = "예금 상세 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/deposit/{deposit-id}")
    public ResponseCustom<DepositDetailRes> getDepositDetail(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @PathVariable(name = "deposit-id") Long depositId
    ) {
        return ResponseCustom.OK(financialProductServiceImpl.getDepositDetail(userPrincipal, depositId));
    }

    @Operation(summary = "적금 상세 정보 조회", description = "적금 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "적금 상세 정보 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SavingDetailRes.class))}),
            @ApiResponse(responseCode = "400", description = "적금 상세 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/saving/{saving-id}")
    public ResponseCustom<SavingDetailRes> getSavingDetail(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @PathVariable(name = "saving-id") Long savingId
    ) {
        return ResponseCustom.OK(financialProductServiceImpl.getSavingDetail(userPrincipal, savingId));
    }

    @Operation(summary = "CMA 정보 조회", description = "CMA 정보를 조건에 따라 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CMA 정보 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SearchFinancialProductRes.class)))}),
            @ApiResponse(responseCode = "400", description = "CMA 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/cma")
    public ResponseCustom<Page<SearchCmaRes>> findCmaProducts(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @ModelAttribute CmaSearchCondition cmaSearchCondition,
            @Parameter(description = "조회 할 페이지와 페이지 크기를 입력해주세요") Pageable pageable
    ) {
        return ResponseCustom.OK(financialProductServiceImpl.findCmaProducts(userPrincipal, cmaSearchCondition, pageable));
    }

    @Operation(summary = "CMA 상세 정보 조회", description = "CMA 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CMA 상세 정보 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CmaDetailRes.class))}),
            @ApiResponse(responseCode = "400", description = "CMAC 상세 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/cma/{cma-id}")
    public ResponseCustom<CmaDetailRes> getCmaDetail(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @PathVariable(name = "cma-id") Long cmaId
    ) {
        return ResponseCustom.OK(financialProductServiceImpl.getCmaDetail(userPrincipal, cmaId));
    }

    @Operation(summary = "은행 등록", description = "은행을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "은행 등록 성공"),
            @ApiResponse(responseCode = "400", description = "은행 등록 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping(value = "/bank")
    public ResponseCustom<Void> bankUpload(
            @RequestPart (name = "bank-upload-req") BankUploadReq bankUploadReq,
            @RequestPart(name = "bank-logo-url") String bankLogoImg
    ) {
        return ResponseCustom.OK(financialProductServiceImpl.bankUpload(bankUploadReq, bankLogoImg));
    }

}