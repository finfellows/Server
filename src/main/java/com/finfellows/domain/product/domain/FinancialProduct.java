package com.finfellows.domain.product.domain;

import com.finfellows.domain.bookmark.domain.FinancialProductBookmark;
import com.finfellows.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Financial_Product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FinancialProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "financial_company_no")
    private String financialCompanyNo;

    @Column(name = "financial_product_code")
    private String financialProductCode;

    @Column(name = "disclosure_month")
    private String disclosureMonth;

    @JoinColumn(name = "bank_name")
    private String bankName;

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

    @OneToMany(mappedBy = "financialProduct", fetch = FetchType.LAZY)
    private List<FinancialProductOption> financialProductOption;

    @OneToMany(mappedBy = "financialProduct", cascade = CascadeType.ALL)
    private List<FinancialProductBookmark> financialProductBookmarkList = new ArrayList<>();

    @Builder
    public FinancialProduct(String financialCompanyNo, String financialProductCode, String disclosureMonth, String bankName, String productName, String joinWay, String maturityInterestRate, String specialCondition, Integer joinDeny, String joinMember, String etcNote, Integer maxLimit, LocalDate disclosureStartDay, LocalDate disclosureEndDay, LocalDateTime financialCompanySubmissionDay, FinancialProductType financialProductType, List<FinancialProductOption> financialProductOption) {
        this.financialCompanyNo = financialCompanyNo;
        this.financialProductCode = financialProductCode;
        this.disclosureMonth = disclosureMonth;
        this.bankName = bankName;
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
        this.financialProductOption = financialProductOption;
    }

}