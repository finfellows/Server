package com.finfellows.domain.product.domain;

import com.finfellows.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Financial_Product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class FinancialProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "financial_company_no")
    private String financialCompanyNo;

    @Column(name = "financial_product_code")
    private String financialProductCode;

    @Column(name = "top_financial_group_no")
    private String topFinancialGroupNo;

    @Column(name = "disclosure_month")
    private String disclosureMonth;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "join_way")
    private String joinWay;

    @Column(name = "maturity_interest_rate")
    private String maturityInterestRate;

    @Column(name = "special_condition")
    private String specialCondition;

    @Column(name = "join_deny")
    private Integer joinDeny;

    @Column(name = "join_member")
    private String joinMember;

    @Column(name = "etc_note")
    private String etcNote;

    @Column(name = "max_limit")
    private Integer maxLimit;

    @Column(name = "disclosure_start_day")
    private LocalDate disclosureStartDay;

    @Column(name = "disclosure_end_day")
    private LocalDate disclosureEndDay;

    @Column(name = "financial_company_submission_day")
    private LocalDateTime financialCompanySubmissionDay;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "financial_product_type")
    private FinancialProductType financialProductType;

    @Builder
    public FinancialProduct(String financialCompanyNo, String financialProductCode, String topFinancialGroupNo, String disclosureMonth, String companyName, String productName, String joinWay, String maturityInterestRate, String specialCondition, Integer joinDeny, String joinMember, String etcNote, Integer maxLimit, LocalDate disclosureStartDay, LocalDate disclosureEndDay, LocalDateTime financialCompanySubmissionDay, FinancialProductType financialProductType) {
        this.financialCompanyNo = financialCompanyNo;
        this.financialProductCode = financialProductCode;
        this.topFinancialGroupNo = topFinancialGroupNo;
        this.disclosureMonth = disclosureMonth;
        this.companyName = companyName;
        this.productName = productName;
        this.joinWay = joinWay;
        this.maturityInterestRate = maturityInterestRate;
        this.specialCondition = specialCondition;
        this.joinDeny = joinDeny;
        this.joinMember = joinMember;
        this.etcNote = etcNote;
        this.maxLimit = maxLimit;
        this.disclosureStartDay = disclosureStartDay;
        this.disclosureEndDay = disclosureEndDay;
        this.financialCompanySubmissionDay = financialCompanySubmissionDay;
        this.financialProductType = financialProductType;
    }

}