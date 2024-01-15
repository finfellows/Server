package com.finfellows.domain.product.domain.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@Subselect("SELECT sfp.id as financial_product_id, sfp.product_name, sfp.bank_name, sfp.maximum_preferred_interest_rate, sfp.interest_rate " +
        "FROM ( " +
        "    SELECT " +
        "        fp.id, " +
        "        fp.product_name, " +
        "        fp.bank_name, " +
        "        fpo.maximum_preferred_interest_rate, " +
        "        fpo.interest_rate, " +
        "        ROW_NUMBER() OVER(PARTITION BY fp.product_name ORDER BY fpo.interest_rate DESC, fpo.maximum_preferred_interest_rate DESC) as rownum " +
        "    FROM financial_product_option fpo " +
        "    LEFT JOIN financial_product fp ON fp.id = fpo.financial_product_id " +
        ") sfp " +
        "WHERE sfp.rownum = 1")
@Immutable
@Synchronize({"financial_product", "financial_product_option"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FinancialProductOptionOrderByDefault {

    @Id
    @Column(name = "financial_product_id")
    private Long financialProductId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "maximum_preferred_interest_rate")
    private String maximumPreferredInterestRate;

    @Column(name = "interest_rate")
    private String interestRate;

}
