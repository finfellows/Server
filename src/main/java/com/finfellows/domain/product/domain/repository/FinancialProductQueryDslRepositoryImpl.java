package com.finfellows.domain.product.domain.repository;

import com.finfellows.domain.product.domain.FinancialProductType;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.response.QSearchFinancialProductRes;
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

import static com.finfellows.domain.product.domain.QFinancialProduct.*;
import static com.finfellows.domain.product.domain.QFinancialProductOption.*;

@RequiredArgsConstructor
@Repository
public class FinancialProductQueryDslRepositoryImpl implements FinancialProductQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchFinancialProductRes> findFinancialProducts(FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable, FinancialProductType financialProductType) {
        List<SearchFinancialProductRes> results = queryFactory
                .select(new QSearchFinancialProductRes(
                        financialProduct.id,
                        financialProduct.productName,
                        financialProduct.companyName,
                        financialProductOption.maximumPreferredInterestRate,
                        financialProductOption.interestRate
                ))
                .from(financialProductOption)
                .leftJoin(financialProductOption.financialProduct, financialProduct)
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
