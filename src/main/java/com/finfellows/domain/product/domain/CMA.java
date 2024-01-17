package com.finfellows.domain.product.domain;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import com.finfellows.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CMA")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CMA extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name="bank_name", unique = true)
    private String bankName;

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

    @OneToMany(mappedBy = "cma", cascade = CascadeType.ALL)
    private List<CmaBookmark> cmaBookmarkList = new ArrayList<>();

    @Builder
    public CMA(String bankName, String productName, String cmaType, String disclosureMonth, String maturityInterestRate, String specialCondition, String joinWay, String etcNote, String productUrl, String depositProtection) {
        this.bankName = bankName;
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