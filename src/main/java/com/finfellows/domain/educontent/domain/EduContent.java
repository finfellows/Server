package com.finfellows.domain.educontent.domain;

import com.finfellows.domain.common.BaseEntity;
import com.finfellows.domain.post.domain.ContentType;
import com.finfellows.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "EduContent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class EduContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name="contentType")
    private ContentType contentType;


    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Builder
    public EduContent(Post post, String title, String content){
        this.post=post;
        this.title=title;
        this.content=content;
        this.contentType = ContentType.EDU_CONTENT;
    }

    public void updateContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
