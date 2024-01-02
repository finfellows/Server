package com.finfellows.domain.bookmark.domain;

import com.finfellows.domain.common.BaseEntity;
import com.finfellows.domain.educontent.domain.EduContent;
import com.finfellows.domain.policyinfo.domain.PolicyInfo;
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
@Where(clause = "status = 'ACTIVE'")
public class Bookmark extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // 금융 뭐하지 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_product_id")
    private FinancialProduct financialProduct;

    // 금융 배우자 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_content_id")
    private EduContent eduContent;

    // 금융 고마워 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_info_id")
    private PolicyInfo policyInfo;

    @Builder
    public Bookmark(FinancialProduct financialProduct, EduContent eduContent, PolicyInfo policyInfo) {
        this.financialProduct = financialProduct;
        this.eduContent = eduContent;
        this.policyInfo = policyInfo;
    }
}
