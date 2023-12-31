package com.finfellows.domain.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FinancialProductId implements Serializable {

    @Column(name = "financial_company_no")
    private String financialCompanyNo;

    @Column(name = "financial_product_code")
    private String financialProductCode;

}

