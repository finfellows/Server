package com.finfellows.domain.policyinfo.presentation;

import com.finfellows.domain.policyinfo.application.PolicyInfoServiceImpl;
import com.finfellows.domain.policyinfo.dto.PolicyInfoDetailRes;
import com.finfellows.domain.policyinfo.dto.PolicyUpdateReq;
import com.finfellows.domain.policyinfo.dto.SearchPolicyInfoRes;
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

@Tag(name = "Policy Information", description = "Policy Information API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/policy-info")
public class PolicyInfoController {

    private final PolicyInfoServiceImpl policyInfoServiceImpl;

    @Operation(summary = "정책 리스트 조회", description = "정책 리스트를 조건에 따라 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정책 리스트 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SearchPolicyInfoRes.class)))}),
            @ApiResponse(responseCode = "400", description = "정책 리스트 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping
    public ResponseCustom<Page<SearchPolicyInfoRes>> findPolicyInfos(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "검색어를 입력해 주세요") @RequestParam(required = false) String searchKeyword,
            @Parameter(description = "조회 할 페이지와 페이지 크기를 입력해주세요") Pageable pageable
    ) {
        return ResponseCustom.OK(policyInfoServiceImpl.findPolicyInfos(userPrincipal, searchKeyword, pageable));
    }

    @Operation(summary = "정책 정보 상세 조회", description = "정책 상세정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정책 상세 정보 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PolicyInfoDetailRes.class))}),
            @ApiResponse(responseCode = "400", description = "정책 상세 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/{policy-id}")
    public ResponseCustom<PolicyInfoDetailRes> findPolicyDetail(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "정책 ID를 입력해 주세요") @PathVariable("policy-id") Long policyId
    ) {
        return ResponseCustom.OK(policyInfoServiceImpl.findPolicyDetail(userPrincipal, policyId));
    }

    @Operation(summary = "정책 삭제", description = "정책을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정책 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "정책 삭제 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PatchMapping("/{policy-id}")
    public ResponseCustom<Void> deletePolicy(
            @Parameter(description = "정책 ID를 입력해 주세요") @PathVariable("policy-id") Long policyId
    ) {
        this.policyInfoServiceImpl.deletePolicy(policyId);
        return ResponseCustom.OK();
    }

    @Operation(summary = "정책 수정", description = "정책을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정책 수정 성공"),
            @ApiResponse(responseCode = "400", description = "정책 수정 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PutMapping("/{policy-id}")
    public ResponseCustom<Void> updatePolicy(
            @Parameter(description = "정책 ID를 입력해 주세요") @PathVariable("policy-id") Long policyId,
            @Parameter(description = "PolicyUpdateReq Schema를 참고해 주세요.") @RequestBody PolicyUpdateReq policyInfoUpdateReq
    ) {
        this.policyInfoServiceImpl.updatePolicy(policyId, policyInfoUpdateReq);
        return ResponseCustom.OK();
    }

}
