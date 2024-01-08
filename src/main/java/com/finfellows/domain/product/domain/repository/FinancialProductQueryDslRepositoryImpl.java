package com.finfellows.domain.product.domain.repository;

import com.finfellows.domain.bookmark.domain.QCmaBookmark;
import com.finfellows.domain.bookmark.domain.QFinancialProductBookmark;
import com.finfellows.domain.product.domain.FinancialProductType;
import com.finfellows.domain.product.domain.QCMA;
import com.finfellows.domain.product.dto.condition.CmaSearchCondition;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.response.QSearchCmaRes;
import com.finfellows.domain.product.dto.response.QSearchFinancialProductRes;
import com.finfellows.domain.product.dto.response.SearchCmaRes;
import com.finfellows.domain.product.dto.response.SearchFinancialProductRes;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.finfellows.domain.product.domain.QCMA.*;
import static com.finfellows.domain.product.domain.QFinancialProduct.*;
import static com.finfellows.domain.product.domain.QFinancialProductOption.*;

@RequiredArgsConstructor
@Repository
public class FinancialProductQueryDslRepositoryImpl implements FinancialProductQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchFinancialProductRes> findFinancialProducts(FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable, FinancialProductType financialProductType, Long userId) {
        QFinancialProductBookmark financialProductBookmark = QFinancialProductBookmark.financialProductBookmark;

        List<SearchFinancialProductRes> results = queryFactory
                .select(new QSearchFinancialProductRes(
                        financialProduct.id,
                        financialProductBookmark.id.isNotNull(),
                        financialProduct.productName,
                        financialProduct.companyName,
                        financialProductOption.maximumPreferredInterestRate,
                        financialProductOption.interestRate
                ))
                .from(financialProductOption)
                .leftJoin(financialProductOption.financialProduct, financialProduct)
                .leftJoin(financialProductBookmark)
                .on(financialProductBookmark.financialProduct.eq(financialProduct).and(financialProductBookmark.user.id.eq(userId)))
                .where(
                        financialProduct.financialProductType.eq(financialProductType),
                        typeEq(financialProductSearchCondition.getType()),
                        bankGroupNoEq(financialProductSearchCondition.getBankGroupNo()),
                        termEq(financialProductSearchCondition.getTerm())
                )
                .orderBy(financialProductOption.maximumPreferredInterestRate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(financialProductOption.count())
                .from(financialProductOption)
                .leftJoin(financialProductOption.financialProduct, financialProduct)
                .where(
                        financialProduct.financialProductType.eq(financialProductType),
                        typeEq(financialProductSearchCondition.getType()),
                        bankGroupNoEq(financialProductSearchCondition.getBankGroupNo()),
                        termEq(financialProductSearchCondition.getTerm())
                );

        // Page 객체 생성
        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<SearchCmaRes> findCmaProducts(CmaSearchCondition cmaSearchCondition, Pageable pageable, Long userId) {
        QCmaBookmark cmaBookmark = QCmaBookmark.cmaBookmark;

        List<SearchCmaRes> results = queryFactory
                .select(new QSearchCmaRes(
                        cMA.id,
                        cmaBookmark.id.isNotNull(),
                        cMA.productName,
                        cMA.companyName,
                        cMA.maturityInterestRate
                ))
                .where(
                        cMA.cmaType.eq(cmaSearchCondition.getCmaType().getValue())
                )
                .from(cMA)
                .leftJoin(cmaBookmark)
                .on(cmaBookmark.cma.eq(cMA).and(cmaBookmark.user.id.eq(userId)))
                .orderBy(cMA.maturityInterestRate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(cMA.count())
                .from(cMA)
                .where(
                        cMA.cmaType.eq(cmaSearchCondition.getCmaType().toString())
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public List<String> findBanks(String bankGroupNo) {
        return queryFactory
                .select(financialProduct.companyName)
                .from(financialProduct)
                .where(bankGroupNoEq(bankGroupNo))
                .distinct()
                .orderBy(financialProduct.companyName.asc())
                .fetch();
    }

    private BooleanExpression bankGroupNoEq(String bankType) {
        return financialProduct.topFinancialGroupNo.eq(Objects.requireNonNullElse(bankType, "020000"));
    }

    private BooleanExpression termEq(Integer term) {
        return term != null ? financialProductOption.savingsTerm.eq(term) : null;
    }

    private BooleanExpression typeEq(String type) {
        return type != null ? financialProductOption.financialProduct.joinWay.contains(type) : null;
    }
  
}
