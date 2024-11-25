package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class ExerciseRoutineJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RoutineJpaEntity routine;

    @ManyToOne
    private ExerciseJpaEntity exercise;

    public ExerciseRoutineJpaEntity(RoutineJpaEntity routine, ExerciseJpaEntity exercise) {
        this.routine = routine;
        this.exercise = exercise;
    }
}
