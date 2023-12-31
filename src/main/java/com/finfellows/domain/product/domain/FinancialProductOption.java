package com.finfellows.domain.product.domain;

import com.finfellows.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "Financial_Product_Option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class FinancialProductOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "disclosure_month")
    private String disclosureMonth;

    @Column(name = "interest_rate_type")
    private String interestRateType;

    @Column(name = "interest_rate_type_name")
    private String interestRateTypeName;

    @Column(name = "reserve_type")
    private String reserveType;

    @Column(name = "reserve_type_name")
    private String reserveTypeName;

    @Column(name = "savings_term")
    private Integer savingsTerm;

    @Column(name = "interest_rate")
    private String interestRate;

    @Column(name = "maximum_preferred_interest_rate")
    private String maximumPreferredInterestRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "financial_company_no", referencedColumnName = "financial_company_no"),
            @JoinColumn(name = "financial_product_code", referencedColumnName = "financial_product_code")
    })
    private FinancialProduct financialProduct;

    @Builder
    public FinancialProductOption(String disclosureMonth, String interestRateType, String interestRateTypeName, String reserveType, String reserveTypeName, Integer savingsTerm, String interestRate, String maximumPreferredInterestRate, FinancialProduct financialProduct) {
        this.disclosureMonth = disclosureMonth;
        this.interestRateType = interestRateType;
        this.interestRateTypeName = interestRateTypeName;
        this.reserveType = reserveType;
        this.reserveTypeName = reserveTypeName;
        this.savingsTerm = savingsTerm;
        this.interestRate = interestRate;
        this.maximumPreferredInterestRate = maximumPreferredInterestRate;
        this.financialProduct = financialProduct;
    }

}
