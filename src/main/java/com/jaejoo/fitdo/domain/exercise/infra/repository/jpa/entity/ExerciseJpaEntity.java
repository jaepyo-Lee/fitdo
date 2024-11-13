package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity;

import jakarta.persistence.*;

@Entity
public class ExerciseJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;
}
