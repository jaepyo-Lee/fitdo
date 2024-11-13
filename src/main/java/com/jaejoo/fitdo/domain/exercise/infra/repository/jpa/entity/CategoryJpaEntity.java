package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity;

import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import jakarta.persistence.*;

@Entity
public class CategoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;
}
