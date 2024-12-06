package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExerciseRoutineJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RoutineJpaEntity routine;

    @Getter
    @ManyToOne
    private ExerciseJpaEntity exercise;

    public ExerciseRoutineJpaEntity(RoutineJpaEntity routine, ExerciseJpaEntity exercise) {
        this.routine = routine;
        this.exercise = exercise;
    }
}
