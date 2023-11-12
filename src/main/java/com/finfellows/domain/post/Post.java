package com.finfellows.domain.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name="Post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)
    private Long id;

    @Column(name="url", nullable = false, unique = true)
    private String url;

    @Column(name="writer_id")
    private Long writerId;

    @Builder
    public Post(String url, Long writerId){
        this.url=url;
        this.writerId=writerId;
    }

}
