package com.finfellows.domain.newscontent.domain;

import com.finfellows.domain.bookmark.domain.NewsContentBookmark;
import com.finfellows.domain.common.BaseEntity;

import com.finfellows.domain.post.domain.ContentType;

import com.finfellows.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="NewsContent")
@NoArgsConstructor
@Getter
@Where(clause = "status = 'ACTIVE'")
public class NewsContent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name="contentType")
    private ContentType contentType;

    @OneToMany(mappedBy = "newsContent", cascade = CascadeType.ALL)
    private List<NewsContentBookmark> newsContentBookmarkList = new ArrayList<>();

    @Builder
    public NewsContent(Post post, String title, String content){
        this.post=post;
        this.title=title;
        this.content=content;
        this.contentType = ContentType.NEWS_CONTENT;

    }

    public void updateContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
