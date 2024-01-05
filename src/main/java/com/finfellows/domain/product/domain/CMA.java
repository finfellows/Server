package com.finfellows.domain.product.domain;

import com.finfellows.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CMA")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CMA extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name="company_name")
    private String companyName;

    @Column(name="product_name")
    private String productName;

    @Column(name="cma_type")
    private String cmaType;

    @Column(name = "disclosure_month")
    private String disclosureMonth;

    @Column(name="maturity_interest_rate")
    private String maturityInterestRate;

    @Column(name="special_condition")
    private String specialCondition;

    @Column(name="join_way")
    private String joinWay;

    @Column(name="etc_note")
    private String etcNote;

    @Column(name="product_url")
    private String productUrl;

    @Column(name="deposit_protection")
    private String depositProtection;

    @Builder
    public CMA(String companyName, String productName, String cmaType, String disclosureMonth, String maturityInterestRate, String specialCondition, String joinWay, String etcNote, String productUrl, String depositProtection) {
        this.companyName = companyName;
        this.productName = productName;
        this.cmaType = cmaType;
        this.disclosureMonth = disclosureMonth;
        this.maturityInterestRate = maturityInterestRate;
        this.specialCondition = specialCondition;
        this.joinWay = joinWay;
        this.etcNote = etcNote;
        this.productUrl = productUrl;
        this.depositProtection = depositProtection;
    }

}