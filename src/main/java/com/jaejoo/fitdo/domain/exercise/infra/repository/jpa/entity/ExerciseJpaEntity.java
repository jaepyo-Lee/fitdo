package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.enumerate.DeleteDelimiter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ExerciseJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private DeleteDelimiter deleteDelimiter;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;

    @Builder
    public ExerciseJpaEntity(Long id, String name, DeleteDelimiter deleteDelimiter, CategoryJpaEntity category) {
        this.id = id;
        this.name = name;
        this.deleteDelimiter = deleteDelimiter;
        this.category = category;
    }

    public ExerciseJpaEntity(String name, CategoryJpaEntity category, DeleteDelimiter deleteDelimiter) {
        this.name = name;
        this.deleteDelimiter = deleteDelimiter;
        this.category = category;
    }

    public static ExerciseJpaEntity create(String name, CategoryJpaEntity category) {
        return new ExerciseJpaEntity(name, category, DeleteDelimiter.IN_USER);
    }
}
