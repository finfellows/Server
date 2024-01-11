package com.finfellows.domain.product.domain.repository;

import com.finfellows.domain.bookmark.domain.QCmaBookmark;
import com.finfellows.domain.bookmark.domain.QFinancialProductBookmark;
import com.finfellows.domain.product.domain.CmaType;
import com.finfellows.domain.product.domain.FinancialProductType;
import com.finfellows.domain.product.domain.QCMA;
import com.finfellows.domain.product.dto.condition.CmaSearchCondition;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.response.QSearchCmaRes;
import com.finfellows.domain.product.dto.response.QSearchFinancialProductRes;
import com.finfellows.domain.product.dto.response.SearchCmaRes;
import com.finfellows.domain.product.dto.response.SearchFinancialProductRes;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
    public Page<SearchFinancialProductRes> findFinancialProductsWithAuthorization(FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable, FinancialProductType financialProductType, Long userId) {
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
                        termEq(financialProductSearchCondition.getTerm()),
                        bankNameEq(financialProductSearchCondition.getBankName())
                );

        // Page 객체 생성
        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<SearchFinancialProductRes> findFinancialProducts(FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable, FinancialProductType financialProductType) {
        List<SearchFinancialProductRes> results = queryFactory
                .select(new QSearchFinancialProductRes(
                        financialProduct.id,
                        Expressions.constant(false),
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
                        termEq(financialProductSearchCondition.getTerm()),
                        bankNameEq(financialProductSearchCondition.getBankName())
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

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<SearchCmaRes> findCmaProductsWithAuthorization(CmaSearchCondition cmaSearchCondition, Pageable pageable, Long userId) {
        QCmaBookmark cmaBookmark = QCmaBookmark.cmaBookmark;

        List<SearchCmaRes> results = queryFactory
                .select(new QSearchCmaRes(
                        cMA.id,
                        cmaBookmark.id.isNotNull(),
                        cMA.productName,
                        cMA.companyName,
                        cMA.maturityInterestRate
                ))
                .from(cMA)
                .leftJoin(cmaBookmark)
                .on(cmaBookmark.cma.eq(cMA).and(cmaBookmark.user.id.eq(userId)))
                .where(
                        cMA.cmaType.eq(CmaType.fromString(cmaSearchCondition.getCmaType()).getValue())
                )
                .orderBy(cMA.maturityInterestRate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(cMA.count())
                .from(cMA)
                .where(
                        cMA.cmaType.eq(CmaType.valueOf(cmaSearchCondition.getCmaType()).getValue())
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<SearchCmaRes> findCmaProducts(CmaSearchCondition cmaSearchCondition, Pageable pageable, Long userId) {
        List<SearchCmaRes> results = queryFactory
                .select(new QSearchCmaRes(
                        cMA.id,
                        Expressions.constant(false),
                        cMA.productName,
                        cMA.companyName,
                        cMA.maturityInterestRate
                ))
                .from(cMA)
                .where(
                        cMA.cmaType.eq(CmaType.fromString(cmaSearchCondition.getCmaType()).getValue())
                )
                .orderBy(cMA.maturityInterestRate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(cMA.count())
                .from(cMA)
                .where(
                        cMA.cmaType.eq(CmaType.valueOf(cmaSearchCondition.getCmaType()).getValue())
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
        if (type == null) return null;
        if (type.equals("누구나 가입")) {
            String[] keywords = {
                    "제한없음", "실명의 개인", "개인(개인사업자 포함)", "만 14세 이상 개인고객",
                    "만 17세 이상 실명의 개인 및 개인사업자", "실명의 개인 또는 개인사업자 (1인 다계좌 가입 가능함)",
                    "거래대상자는 제한을 두지 않으나, 국가 및 지방자치단체는 거래 불가능", "만 19세 이상의 개인",
                    "인터넷 및 모바일뱅킹 사용자", "누구나 가입 가능", "모든 고객",
                    "만 19세 이상 실명의 개인고객 (1인 1계좌 한정)", "실명의 개인, 법인", "인터넷뱅킹, 스마트폰뱅킹 전용"
            };

            BooleanExpression expression = financialProductOption.financialProduct.joinMember.contains(type);

            for (String keyword : keywords) {
                expression = expression.or(financialProductOption.financialProduct.joinMember.contains(keyword));
            }

            return expression;
        }
        return financialProductOption.financialProduct.joinMember.contains(type)
                .or(financialProductOption.financialProduct.productName.contains(type))
                .or(financialProductOption.financialProduct.specialCondition.contains(type))
                .or(financialProductOption.financialProduct.etcNote.contains(type));
    }

    private BooleanExpression bankNameEq(String bankName) {
        return bankName != null ? financialProductOption.financialProduct.companyName.contains(bankName) : null;
    }

}