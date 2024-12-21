package com.jaejoo.fitdomysql.domain.user.repository.jpa.entity;

import jakarta.persistence.*;

@Entity
public class ScoreJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    private Double score;
}
