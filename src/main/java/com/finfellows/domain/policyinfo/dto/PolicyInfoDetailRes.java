package com.finfellows.domain.policyinfo.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PolicyInfoDetailRes {

    @Schema(description = "좋아요 여부")
    private Boolean isLiked;
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

    @QueryProjection
    public PolicyInfoDetailRes(Boolean isLiked, String polyBizSjNm, String polyItcnCn, String sporCn, String bizPrdCn, String rqutPrdCn, String sporScvl, String ageInfo, String prcpCn, String accrRqisCn, String majrRquisCn, String empmSttsCn, String spizRlmRqisCn, String aditRscn, String prcpLmttTrgtCn, String rqutProcCn, String jdgnPresCn, String rqutUrla, String pstnPaprCn) {
        this.isLiked = isLiked;
        this.polyBizSjNm = polyBizSjNm;
        this.polyItcnCn = polyItcnCn;
        this.sporCn = sporCn;
        this.bizPrdCn = bizPrdCn;
        this.rqutPrdCn = rqutPrdCn;
        this.sporScvl = sporScvl;
        this.ageInfo = ageInfo;
        this.prcpCn = prcpCn;
        this.accrRqisCn = accrRqisCn;
        this.majrRquisCn = majrRquisCn;
        this.empmSttsCn = empmSttsCn;
        this.spizRlmRqisCn = spizRlmRqisCn;
        this.aditRscn = aditRscn;
        this.prcpLmttTrgtCn = prcpLmttTrgtCn;
        this.rqutProcCn = rqutProcCn;
        this.jdgnPresCn = jdgnPresCn;
        this.rqutUrla = rqutUrla;
        this.pstnPaprCn = pstnPaprCn;
    }

}
