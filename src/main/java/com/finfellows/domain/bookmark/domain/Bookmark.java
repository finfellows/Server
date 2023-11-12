package com.finfellows.domain.bookmark.domain;

import com.finfellows.domain.common.BaseEntity;
import com.finfellows.domain.product.domain.FinancialProduct;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "Bookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // 금융 뭐하지 id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "financial_product_id")
    private FinancialProduct financialProduct;

    // 금융 배우자 id
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "financial_study_id")
    //private FinancialStudy finanacialStudy;

    // 금융 고마워 id
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "finanacial_appreciation_id")
    //private FinancialAppreciation financialAppreciation;



//    @Builder
//    public Bookmark(FinancialProduct financialProduct) {
//        this.financialProduct = financialProduct;
//    }
}
