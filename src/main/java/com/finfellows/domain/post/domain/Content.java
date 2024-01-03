package com.finfellows.domain.post.domain;

import com.finfellows.domain.common.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name="Content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post_id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;


    @Builder
    public Content(Post post_id, String title, String content){
        this.post_id=post_id;
        this.title=title;
        this.content=content;
    }

    public void updateContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
