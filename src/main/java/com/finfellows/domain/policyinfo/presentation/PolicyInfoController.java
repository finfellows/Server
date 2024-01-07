package com.finfellows.domain.policyinfo.presentation;

import com.finfellows.domain.policyinfo.application.PolicyInfoServiceImpl;
import com.finfellows.domain.policyinfo.dto.SearchPolicyInfoRes;
import com.finfellows.global.config.security.token.CurrentUser;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.ErrorResponse;
import com.finfellows.global.payload.PagedResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseCustom<PagedResponse<SearchPolicyInfoRes>> findPolicyInfos(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "검색어를 입력해 주세요") @RequestParam(required = false) String searchKeyword,
            @Parameter(description = "조회 할 페이지와 페이지 크기를 입력해주세요") Pageable pageable
    ) {
        return ResponseCustom.OK(policyInfoServiceImpl.findPolicyInfos(userPrincipal, searchKeyword, pageable));
    }

}
