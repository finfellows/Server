package com.finfellows.domain.policyinfo.domain;

import com.finfellows.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "PolicyInfo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class PolicyInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String polyBizSjNm;

    private String polyItcnCn;

    private String sporCn;

    private String bizPrdCn;

    private String rqutPrdCn;

    private String sporScvl;

    private String ageInfo;

    private String prcpCn;

    private String accrRqisCn;

    private String majrRqisCn;

    private String empmSttsCn;

    private String splzRlmRqisCn;

    private String aditRscn;

    private String prcpLmttTrgtCn;

    private String rqutProcCn;

    private String jdgnPresCn;

    private String rqutUrla;

    private String pstnPaprCn;

    @Builder
    public PolicyInfo(String polyBizSjNm, String polyItcnCn, String sporCn, String bizPrdCn, String rqutPrdCn, String sporScvl, String ageInfo, String prcpCn, String accrRqisCn, String majrRqisCn, String empmSttsCn, String splzRlmRqisCn, String aditRscn, String prcpLmttTrgtCn, String rqutProcCn, String jdgnPresCn, String rqutUrla, String pstnPaprCn) {
        this.polyBizSjNm = polyBizSjNm;
        this.polyItcnCn = polyItcnCn;
        this.sporCn = sporCn;
        this.bizPrdCn = bizPrdCn;
        this.rqutPrdCn = rqutPrdCn;
        this.sporScvl = sporScvl;
        this.ageInfo = ageInfo;
        this.prcpCn = prcpCn;
        this.accrRqisCn = accrRqisCn;
        this.majrRqisCn = majrRqisCn;
        this.empmSttsCn = empmSttsCn;
        this.splzRlmRqisCn = splzRlmRqisCn;
        this.aditRscn = aditRscn;
        this.prcpLmttTrgtCn = prcpLmttTrgtCn;
        this.rqutProcCn = rqutProcCn;
        this.jdgnPresCn = jdgnPresCn;
        this.rqutUrla = rqutUrla;
        this.pstnPaprCn = pstnPaprCn;
    }
}
