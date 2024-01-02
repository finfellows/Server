package com.finfellows.domain.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

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

    @Column(name="sequence", nullable = false)
    private Long sequence;

    @Column(name="content")
    private String content;

    @Builder
    public Content(Post post_id, Long sequence, String content){
        this.post_id=post_id;
        this.sequence=sequence;
        this.content=content;
    }

}
