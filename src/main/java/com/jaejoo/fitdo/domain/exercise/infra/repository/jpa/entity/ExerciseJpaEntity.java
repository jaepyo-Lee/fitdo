package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class ExerciseJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;

    @Builder
    public ExerciseJpaEntity(String name, CategoryJpaEntity category) {
        this.name = name;
        this.category = category;
    }
}
