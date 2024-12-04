package com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity;

import jakarta.persistence.*;

@Entity
public class ScoreJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    private Double score;
}
