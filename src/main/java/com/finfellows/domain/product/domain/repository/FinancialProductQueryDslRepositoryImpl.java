package com.finfellows.domain.product.domain.repository;

import com.finfellows.domain.bookmark.domain.QCmaBookmark;
import com.finfellows.domain.bookmark.domain.QFinancialProductBookmark;
import com.finfellows.domain.product.domain.*;
import com.finfellows.domain.product.dto.condition.CmaSearchCondition;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.response.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.finfellows.domain.product.domain.QBank.*;
import static com.finfellows.domain.product.domain.QCMA.*;
import static com.finfellows.domain.product.domain.QFinancialProduct.*;
import static com.finfellows.domain.product.domain.QFinancialProductOption.*;
import static com.finfellows.domain.product.domain.repository.QFinancialProductOptionOrderByDefault.*;
import static com.finfellows.domain.product.domain.repository.QFinancialProductOptionOrderByMax.*;

@RequiredArgsConstructor
@Repository
public class FinancialProductQueryDslRepositoryImpl implements FinancialProductQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchFinancialProductRes> findFinancialProductsWithAuthorization(FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable, FinancialProductType financialProductType, Long userId) {
        QFinancialProductBookmark financialProductBookmark = QFinancialProductBookmark.financialProductBookmark;
        String interestRateType = financialProductSearchCondition.getInterestRateType();
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        List<SearchFinancialProductRes> results;
        JPAQuery<Long> countQuery;

        if ("DEFAULT".equals(interestRateType)) {
            orderSpecifiers.add(financialProductOptionOrderByDefault.interestRate.desc());
            orderSpecifiers.add(financialProductOptionOrderByDefault.maximumPreferredInterestRate.desc());

            results = queryFactory
                    .select(new QSearchFinancialProductRes(
                            financialProductOptionOrderByDefault.financialProductId,
                            financialProductBookmark.id.isNotNull(),
                            financialProductOptionOrderByDefault.productName,
                            financialProductOptionOrderByDefault.companyName,
                            bank.bankLogoUrl,
                            financialProductOptionOrderByDefault.maximumPreferredInterestRate,
                            financialProductOptionOrderByDefault.interestRate
                    ))
                    .from(financialProductOptionOrderByDefault)
                    .leftJoin(bank).on(financialProductOptionOrderByDefault.companyName.contains(bank.bankName))
                    .leftJoin(financialProduct).on(financialProductOptionOrderByDefault.financialProductId.eq(financialProduct.id))
                    .leftJoin(financialProductBookmark).on(financialProduct.id.eq(financialProductBookmark.financialProduct.id).and(financialProductBookmark.user.id.eq(userId)))
                    .where(
                            financialProduct.financialProductType.eq(financialProductType),
                            typeEq(financialProductSearchCondition.getTypes()),
                            bankGroupNoEq(financialProductSearchCondition.getBankGroupNos()),
                            termEq(financialProductSearchCondition.getTerms())
                    )
                    .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            countQuery = queryFactory
                    .select(financialProductOptionOrderByDefault.count())
                    .from(financialProductOptionOrderByDefault)
                    .leftJoin(financialProduct).on(financialProductOptionOrderByDefault.financialProductId.eq(financialProduct.id))
                    .where(
                            financialProduct.financialProductType.eq(financialProductType),
                            typeEq(financialProductSearchCondition.getTypes()),
                            bankGroupNoEq(financialProductSearchCondition.getBankGroupNos()),
                            termEq(financialProductSearchCondition.getTerms()),
                            bankNameEq(financialProductSearchCondition.getBankNames())
                    );
        } else {
            orderSpecifiers.add(financialProductOptionOrderByMax.maximumPreferredInterestRate.desc());
            orderSpecifiers.add(financialProductOptionOrderByMax.interestRate.desc());

            results = queryFactory
                    .select(new QSearchFinancialProductRes(
                            financialProductOptionOrderByMax.financialProductId,
                            financialProductBookmark.id.isNotNull(),
                            financialProductOptionOrderByMax.productName,
                            financialProductOptionOrderByMax.companyName,
                            bank.bankLogoUrl,
                            financialProductOptionOrderByMax.maximumPreferredInterestRate,
                            financialProductOptionOrderByMax.interestRate
                    ))
                    .from(financialProductOptionOrderByMax)
                    .leftJoin(financialProduct).on(financialProductOptionOrderByMax.financialProductId.eq(financialProduct.id))
                    .leftJoin(bank).on(financialProductOptionOrderByMax.companyName.contains(bank.bankName))
                    .leftJoin(financialProductBookmark).on(financialProduct.id.eq(financialProductBookmark.financialProduct.id).and(financialProductBookmark.user.id.eq(userId)))
                    .where(
                            financialProduct.financialProductType.eq(financialProductType),
                            typeEq(financialProductSearchCondition.getTypes()),
                            bankGroupNoEq(financialProductSearchCondition.getBankGroupNos()),
                            termEq(financialProductSearchCondition.getTerms())
                    )
                    .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            countQuery = queryFactory
                    .select(financialProductOptionOrderByMax.count())
                    .from(financialProductOptionOrderByMax)
                    .leftJoin(financialProduct).on(financialProductOptionOrderByMax.financialProductId.eq(financialProduct.id))
                    .where(
                            financialProduct.financialProductType.eq(financialProductType),
                            typeEq(financialProductSearchCondition.getTypes()),
                            bankGroupNoEq(financialProductSearchCondition.getBankGroupNos()),
                            termEq(financialProductSearchCondition.getTerms()),
                            bankNameEq(financialProductSearchCondition.getBankNames())
                    );
        }

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<SearchFinancialProductRes> findFinancialProducts(FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable, FinancialProductType financialProductType) {
        String interestRateType = financialProductSearchCondition.getInterestRateType();
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        List<SearchFinancialProductRes> results;
        JPAQuery<Long> countQuery;

        if ("DEFAULT".equals(interestRateType)) {
            orderSpecifiers.add(financialProductOptionOrderByDefault.interestRate.desc());
            orderSpecifiers.add(financialProductOptionOrderByDefault.maximumPreferredInterestRate.desc());

            results = queryFactory
                    .select(new QSearchFinancialProductRes(
                            financialProductOptionOrderByDefault.financialProductId,
                            Expressions.constant(false),
                            financialProductOptionOrderByDefault.productName,
                            financialProductOptionOrderByDefault.companyName,
                            bank.bankLogoUrl,
                            financialProductOptionOrderByDefault.maximumPreferredInterestRate,
                            financialProductOptionOrderByDefault.interestRate
                    ))
                    .from(financialProductOptionOrderByDefault)
                    .leftJoin(bank).on(financialProductOptionOrderByDefault.companyName.contains(bank.bankName))
                    .leftJoin(financialProduct).on(financialProductOptionOrderByDefault.financialProductId.eq(financialProduct.id))
                    .where(
                            financialProduct.financialProductType.eq(financialProductType),
                            typeEq(financialProductSearchCondition.getTypes()),
                            bankGroupNoEq(financialProductSearchCondition.getBankGroupNos()),
                            termEq(financialProductSearchCondition.getTerms())
                    )
                    .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            countQuery = queryFactory
                    .select(financialProductOptionOrderByDefault.count())
                    .from(financialProductOptionOrderByDefault)
                    .leftJoin(financialProduct).on(financialProductOptionOrderByDefault.financialProductId.eq(financialProduct.id))
                    .where(
                            financialProduct.financialProductType.eq(financialProductType),
                            typeEq(financialProductSearchCondition.getTypes()),
                            bankGroupNoEq(financialProductSearchCondition.getBankGroupNos()),
                            termEq(financialProductSearchCondition.getTerms()),
                            bankNameEq(financialProductSearchCondition.getBankNames())
                    );
        } else {
            orderSpecifiers.add(financialProductOptionOrderByMax.maximumPreferredInterestRate.desc());
            orderSpecifiers.add(financialProductOptionOrderByMax.interestRate.desc());

            results = queryFactory
                    .select(new QSearchFinancialProductRes(
                            financialProductOptionOrderByMax.financialProductId,
                            Expressions.constant(false),
                            financialProductOptionOrderByMax.productName,
                            financialProductOptionOrderByMax.companyName,
                            bank.bankLogoUrl,
                            financialProductOptionOrderByMax.maximumPreferredInterestRate,
                            financialProductOptionOrderByMax.interestRate
                    ))
                    .from(financialProductOptionOrderByMax)
                    .leftJoin(financialProduct).on(financialProductOptionOrderByMax.financialProductId.eq(financialProduct.id))
                    .leftJoin(bank).on(financialProductOptionOrderByMax.companyName.contains(bank.bankName))
                    .where(
                            financialProduct.financialProductType.eq(financialProductType),
                            typeEq(financialProductSearchCondition.getTypes()),
                            bankGroupNoEq(financialProductSearchCondition.getBankGroupNos()),
                            termEq(financialProductSearchCondition.getTerms())
                    )
                    .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            countQuery = queryFactory
                    .select(financialProductOptionOrderByMax.count())
                    .from(financialProductOptionOrderByMax)
                    .leftJoin(financialProduct).on(financialProductOptionOrderByMax.financialProductId.eq(financialProduct.id))
                    .where(
                            financialProduct.financialProductType.eq(financialProductType),
                            typeEq(financialProductSearchCondition.getTypes()),
                            bankGroupNoEq(financialProductSearchCondition.getBankGroupNos()),
                            termEq(financialProductSearchCondition.getTerms()),
                            bankNameEq(financialProductSearchCondition.getBankNames())
                    );
        }

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
                        bank.bankLogoUrl,
                        cMA.maturityInterestRate
                ))
                .from(cMA)
                .leftJoin(cmaBookmark)
                .on(cmaBookmark.cma.eq(cMA).and(cmaBookmark.user.id.eq(userId)))
                .leftJoin(bank)
                .on(cMA.companyName.contains(bank.bankName))
                .where(
                        cmaTypeEq(cmaSearchCondition.getCmaTypes()),
                        cmaBankNameEq(cmaSearchCondition.getBankNames())
                )
                .orderBy(cMA.maturityInterestRate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(cMA.count())
                .from(cMA)
                .where(
                        cmaTypeEq(cmaSearchCondition.getCmaTypes()),
                        cmaBankNameEq(cmaSearchCondition.getBankNames())
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<SearchCmaRes> findCmaProducts(CmaSearchCondition cmaSearchCondition, Pageable pageable) {
        List<SearchCmaRes> results = queryFactory
                .select(new QSearchCmaRes(
                        cMA.id,
                        Expressions.constant(false),
                        cMA.productName,
                        cMA.companyName,
                        bank.bankLogoUrl,
                        cMA.maturityInterestRate
                ))
                .from(cMA)
                .leftJoin(bank)
                .on(cMA.companyName.contains(bank.bankName))
                .where(
                        cmaTypeEq(cmaSearchCondition.getCmaTypes()),
                        cmaBankNameEq(cmaSearchCondition.getBankNames())
                )
                .orderBy(cMA.maturityInterestRate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(cMA.count())
                .from(cMA)
                .where(
                        cmaTypeEq(cmaSearchCondition.getCmaTypes()),
                        cmaBankNameEq(cmaSearchCondition.getBankNames())
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public List<SearchBankRes> findBanks(String[] bankGroupNos) {
        return queryFactory
                .select(new QSearchBankRes(
                        financialProduct.companyName,
                        bank.bankLogoUrl
                ))
                .from(financialProduct)
                .leftJoin(bank)
                .on(financialProduct.companyName.contains(bank.bankName))
                .where(bankGroupNoEq(bankGroupNos))
                .distinct()
                .orderBy(financialProduct.companyName.asc())
                .fetch();
    }

    private BooleanExpression bankGroupNoEq(String[] bankTypes) {
        if (bankTypes == null || bankTypes.length == 0) return null;
        return financialProduct.topFinancialGroupNo.in(bankTypes);
    }

    private BooleanExpression termEq(Integer[] terms) {
        if (terms == null || terms.length == 0) return null;
        return financialProductOption.savingsTerm.in(terms);
    }

    private BooleanExpression typeEq(String[] types) {
        if (types == null || types.length == 0) {
            return null;
        }

        BooleanExpression expression = null;

        for (String type : types) {
            if (type.equals("누구나 가입")) {
                String[] keywords = {
                        "제한없음", "실명의 개인", "개인(개인사업자 포함)", "만 14세 이상 개인고객",
                        "만 17세 이상 실명의 개인 및 개인사업자", "실명의 개인 또는 개인사업자 (1인 다계좌 가입 가능함)",
                        "거래대상자는 제한을 두지 않으나, 국가 및 지방자치단체는 거래 불가능", "만 19세 이상의 개인",
                        "인터넷 및 모바일뱅킹 사용자", "누구나 가입 가능", "모든 고객",
                        "만 19세 이상 실명의 개인고객 (1인 1계좌 한정)", "실명의 개인, 법인", "인터넷뱅킹, 스마트폰뱅킹 전용"
                };

                for (String keyword : keywords) {
                    if (expression == null) {
                        expression = financialProductOption.financialProduct.joinMember.contains(keyword);
                    } else {
                        expression = expression.or(financialProductOption.financialProduct.joinMember.contains(keyword));
                    }
                }
            } else {
                if (expression == null) {
                    expression = financialProductOption.financialProduct.joinMember.contains(type)
                            .or(financialProductOption.financialProduct.productName.contains(type))
                            .or(financialProductOption.financialProduct.specialCondition.contains(type))
                            .or(financialProductOption.financialProduct.etcNote.contains(type));
                } else {
                    expression = expression.or(financialProductOption.financialProduct.joinMember.contains(type))
                            .or(financialProductOption.financialProduct.productName.contains(type))
                            .or(financialProductOption.financialProduct.specialCondition.contains(type))
                            .or(financialProductOption.financialProduct.etcNote.contains(type));
                }
            }
        }

        return expression;
    }


    private BooleanExpression bankNameEq(String[] bankNames) {
        if (bankNames == null || bankNames.length == 0) return null;
        BooleanExpression expression = financialProductOption.financialProduct.companyName.contains(bankNames[0]);
        for (int i = 1; i < bankNames.length; i++) {
            expression = expression.or(financialProductOption.financialProduct.companyName.contains(bankNames[i]));
        }
        return expression;
    }

    private BooleanExpression cmaTypeEq(String[] cmaTypes) {
        if (cmaTypes == null || cmaTypes.length == 0) {
            return null;
        }

        BooleanExpression expression = null;
        for (String cmaType : cmaTypes) {
            CmaType type = CmaType.fromString(cmaType);
            if (expression == null) {
                expression = cMA.cmaType.eq(type.getValue());
            } else {
                expression = expression.or(cMA.cmaType.eq(type.getValue()));
            }
        }

        return expression;
    }

    private BooleanExpression cmaBankNameEq(String[] bankNames) {
        if (bankNames == null || bankNames.length == 0) {
            return null;
        }

        BooleanExpression expression = null;
        for (String bankName : bankNames) {
            if (expression == null) {
                expression = cMA.companyName.contains(bankName);
            } else {
                expression = expression.or(cMA.companyName.contains(bankName));
            }
        }

        return expression;
    }

}