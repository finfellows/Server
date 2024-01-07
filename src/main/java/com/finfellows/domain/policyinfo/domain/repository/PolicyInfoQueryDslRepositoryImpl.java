package com.finfellows.domain.policyinfo.domain.repository;

import com.finfellows.domain.bookmark.domain.QPolicyInfoBookmark;
import com.finfellows.domain.policyinfo.domain.QPolicyInfo;
import com.finfellows.domain.policyinfo.dto.QSearchPolicyInfoRes;
import com.finfellows.domain.policyinfo.dto.SearchPolicyInfoRes;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    public Page<SearchPolicyInfoRes> findPolicyInfos(String searchKeyword, Pageable pageable, Long userId) {
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

    private BooleanExpression searchEq(String searchKeyword) {
        return searchKeyword != null ? policyInfo.polyBizSjNm.contains(searchKeyword).or(policyInfo.polyItcnCn.contains(searchKeyword)) : null;
    }

}
