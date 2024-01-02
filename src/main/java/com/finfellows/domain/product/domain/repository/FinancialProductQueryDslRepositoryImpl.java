package com.finfellows.domain.product.domain.repository;

import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.product.domain.FinancialProductType;
import com.finfellows.domain.product.domain.QFinancialProduct;
import com.finfellows.domain.product.domain.QFinancialProductOption;
import com.finfellows.domain.product.dto.condition.DepositSearchCondition;
import com.finfellows.domain.product.dto.response.QSearchDepositRes;
import com.finfellows.domain.product.dto.response.SearchDepositRes;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.finfellows.domain.product.domain.QFinancialProduct.*;
import static com.finfellows.domain.product.domain.QFinancialProductOption.*;

@RequiredArgsConstructor
@Repository
public class FinancialProductQueryDslRepositoryImpl implements FinancialProductQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchDepositRes> findFinancialProducts(DepositSearchCondition depositSearchCondition, Pageable pageable) {
        List<SearchDepositRes> results = queryFactory
                .select(new QSearchDepositRes(
                        financialProduct.id,
                        financialProduct.productName,
                        financialProduct.companyName,
                        financialProductOption.maximumPreferredInterestRate,
                        financialProductOption.interestRate
                ))
                .from(financialProductOption)
                .leftJoin(financialProductOption.financialProduct, financialProduct)
                .where(
                        financialProduct.financialProductType.eq(FinancialProductType.DEPOSIT),
                        typeEq(depositSearchCondition.getType()),
                        preferentialConditionEq(depositSearchCondition.getPreferentialCondition()),
                        termEq(depositSearchCondition.getTerm())
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
                        financialProduct.financialProductType.eq(FinancialProductType.DEPOSIT),
                        typeEq(depositSearchCondition.getType()),
                        preferentialConditionEq(depositSearchCondition.getPreferentialCondition()),
                        termEq(depositSearchCondition.getTerm())
                );

        // Page 객체 생성
        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    private BooleanExpression termEq(Integer term) {
        return term != null ? financialProductOption.savingsTerm.eq(term) : null;
    }

    private BooleanExpression typeEq(String type) {
        return type != null ? financialProductOption.financialProduct.joinWay.contains(type) : null;
    }

    private BooleanExpression preferentialConditionEq(String preferentialCondition) {
        return preferentialCondition != null ? financialProductOption.financialProduct.specialCondition.contains(preferentialCondition) : null;
    }

}
