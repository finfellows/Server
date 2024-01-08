package com.finfellows.domain.policyinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PolicyUpdateReq {

    @Schema(description = "정책 이름")
    private String polyBizSjNm;
    @Schema(description = "정책 한 줄 소개")
    private String polyItcnCn;
    @Schema(description = "정책 내용")
    private String sporCn;
    @Schema(description = "운영 기간")
    private String bizPrdCn;
    @Schema(description = "신청 기간")
    private String rqutPrdCn;
    @Schema(description = "지원 규모")
    private String sporScvl;
    @Schema(description = "연령")
    private String ageInfo;
    @Schema(description = "거주지 및 소득")
    private String prcpCn;
    @Schema(description = "학력")
    private String accrRqisCn;
    @Schema(description = "전공")
    private String majrRquisCn;
    @Schema(description = "취업 상태")
    private String empmSttsCn;
    @Schema(description = "특화 분야")
    private String spizRlmRqisCn;
    @Schema(description = "추가 단서 사항")
    private String aditRscn;
    @Schema(description = "참여 제한 대상")
    private String prcpLmttTrgtCn;
    @Schema(description = "신청 절차")
    private String rqutProcCn;
    @Schema(description = "심사 및 발표")
    private String jdgnPresCn;
    @Schema(description = "신청 사이트")
    private String rqutUrla;
    @Schema(description = "제출 서류")
    private String pstnPaprCn;

}
