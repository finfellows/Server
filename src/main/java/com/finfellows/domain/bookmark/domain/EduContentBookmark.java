package com.finfellows.domain.bookmark.domain;

import com.finfellows.domain.common.BaseEntity;
import com.finfellows.domain.educontent.domain.EduContent;
import com.finfellows.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "EduContentBookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class EduContentBookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 금융 배우자 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_content_id")
    private EduContent eduContent;

    @Builder
    public EduContentBookmark(User user, EduContent eduContent) {
        this.user = user;
        this.eduContent = eduContent;
    }
}
