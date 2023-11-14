package com.finfellows.domain.user.domain;

import com.finfellows.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "'User'")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "provider_id", nullable = false, unique = true, updatable = false)
    private String providerId;

    @Builder
    public User(String email, String name, String providerId) {
        this.email = email;
        this.name = name;
        this.providerId = providerId;
    }

}
