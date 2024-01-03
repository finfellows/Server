package com.finfellows.domain.user.domain;

import com.finfellows.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "User")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "provider_id", unique = true, updatable = false)
    private Long providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;



    @Builder
    public User(String email, String name, Long providerId, Role role) {
        this.email = email;
        this.name = name;
        this.providerId = providerId;
        this.role = role;
    }
}
