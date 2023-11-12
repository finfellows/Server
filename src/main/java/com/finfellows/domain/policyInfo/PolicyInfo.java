package com.finfellows.domain.policyInfo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "PolicyInfo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class PolicyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name="url", nullable = false, unique = true)
    private String url;

    @Column(name="writer_id")
    private Long writerId;

    @Builder
    public PolicyInfo(String url, Long writerId){
        this.url=url;
        this.writerId=writerId;
    }
}
