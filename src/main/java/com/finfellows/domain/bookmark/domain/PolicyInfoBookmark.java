package com.finfellows.domain.bookmark.domain;

import com.finfellows.domain.common.BaseEntity;
import com.finfellows.domain.policyinfo.domain.PolicyInfo;
import com.finfellows.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "PolicyInfoBookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class PolicyInfoBookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 금융 고마워 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_info_id")
    private PolicyInfo policyInfo;

    @Builder
    public PolicyInfoBookmark(User user, PolicyInfo policyInfo) {
        this.user = user;
        this.policyInfo = policyInfo;
    }
}
