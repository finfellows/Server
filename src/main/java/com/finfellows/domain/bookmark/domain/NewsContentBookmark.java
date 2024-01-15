package com.finfellows.domain.bookmark.domain;

import com.finfellows.domain.common.BaseEntity;
import com.finfellows.domain.newscontent.domain.NewsContent;
import com.finfellows.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "NewsContentBookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class NewsContentBookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_content_id")
    private NewsContent newsContent;

    @Builder
    public NewsContentBookmark(User user, NewsContent newsContent) {
        this.user = user;
        this.newsContent = newsContent;
    }
}
