package com.finfellows.domain.policyinfo.domain.repository;

import com.finfellows.domain.bookmark.domain.QPolicyInfoBookmark;
import com.finfellows.domain.policyinfo.domain.QPolicyInfo;
import com.finfellows.domain.policyinfo.dto.PolicyInfoDetailRes;
import com.finfellows.domain.policyinfo.dto.QPolicyInfoDetailRes;
import com.finfellows.domain.policyinfo.dto.QSearchPolicyInfoRes;
import com.finfellows.domain.policyinfo.dto.SearchPolicyInfoRes;
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

import static com.finfellows.domain.policyinfo.domain.QPolicyInfo.*;

@RequiredArgsConstructor
@Repository
public class PolicyInfoQueryDslRepositoryImpl implements PolicyInfoQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchPolicyInfoRes> findPolicyInfosWithAuthorization(String searchKeyword, Pageable pageable, Long userId) {
        QPolicyInfoBookmark policyInfoBookmark = QPolicyInfoBookmark.policyInfoBookmark;

        List<SearchPolicyInfoRes> results = queryFactory
                .select(new QSearchPolicyInfoRes(
                        policyInfo.id,
                        policyInfo.polyBizSjNm,
                        policyInfo.polyItcnCn,
                        policyInfoBookmark.id.isNotNull()
                ))
                .from(policyInfo)
                .leftJoin(policyInfoBookmark)
                .on(policyInfoBookmark.policyInfo.eq(policyInfo).and(policyInfoBookmark.user.id.eq(userId)))
                .where(
                        searchEq(searchKeyword)
                )
                .orderBy(policyInfo.polyBizSjNm.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(policyInfo.count())
                .from(policyInfo)
                .where(
                        searchEq(searchKeyword)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<SearchPolicyInfoRes> findPolicyInfos(String searchKeyword, Pageable pageable) {
        List<SearchPolicyInfoRes> results = queryFactory
                .select(new QSearchPolicyInfoRes(
                        policyInfo.id,
                        policyInfo.polyBizSjNm,
                        policyInfo.polyItcnCn,
                        Expressions.constant(false)
                ))
                .from(policyInfo)
                .where(
                        searchEq(searchKeyword)
                )
                .orderBy(policyInfo.polyBizSjNm.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(policyInfo.count())
                .from(policyInfo)
                .where(
                        searchEq(searchKeyword)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    @Override
    public PolicyInfoDetailRes findPolicyDetail(Long policyId, Long userId) {
        QPolicyInfoBookmark policyInfoBookmark = QPolicyInfoBookmark.policyInfoBookmark;

        List<PolicyInfoDetailRes> result = queryFactory
                .select(new QPolicyInfoDetailRes(
                        policyInfoBookmark.id.isNotNull(),
                        policyInfo.polyBizSjNm,
                        policyInfo.polyItcnCn,
                        policyInfo.sporCn,
                        policyInfo.bizPrdCn,
                        policyInfo.rqutPrdCn,
                        policyInfo.sporScvl,
                        policyInfo.ageInfo,
                        policyInfo.prcpCn,
                        policyInfo.accrRqisCn,
                        policyInfo.majrRqisCn,
                        policyInfo.empmSttsCn,
                        policyInfo.splzRlmRqisCn,
                        policyInfo.aditRscn,
                        policyInfo.prcpLmttTrgtCn,
                        policyInfo.rqutProcCn,
                        policyInfo.jdgnPresCn,
                        policyInfo.rqutUrla,
                        policyInfo.pstnPaprCn
                ))
                .from(policyInfo)
                .leftJoin(policyInfoBookmark)
                .on(policyInfoBookmark.policyInfo.eq(policyInfo).and(policyInfoBookmark.user.id.eq(userId)))
                .where(
                        policyInfo.id.eq(policyId)
                )
                .fetch();
        return result.get(0);
    }

    private BooleanExpression searchEq(String searchKeyword) {
        return searchKeyword != null ? policyInfo.polyBizSjNm.contains(searchKeyword).or(policyInfo.polyItcnCn.contains(searchKeyword)) : null;
    }

}
