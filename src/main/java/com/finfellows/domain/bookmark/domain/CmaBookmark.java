package com.finfellows.domain.bookmark.domain;

import com.finfellows.domain.common.BaseEntity;
import com.finfellows.domain.product.domain.CMA;
import com.finfellows.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "CmaBookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class CmaBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 금융 뭐하지 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cma_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CMA cma;

    @Builder
    public CmaBookmark(User user, CMA cma) {
        this.user = user;
        this.cma = cma;
    }

}
