package com.finfellows.domain.post.domain;

import com.finfellows.domain.common.BaseEntity;
import com.finfellows.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name="Post")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="writer_id")
    private User writer;

    @Builder
    public Post(User writer){
        this.writer=writer;
    }

}
