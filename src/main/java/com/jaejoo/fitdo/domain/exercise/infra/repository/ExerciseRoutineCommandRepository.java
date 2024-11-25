package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;

import java.util.List;

public interface ExerciseRoutineCommandRepository {
    ExerciseRoutineJpaEntity save(ExerciseRoutineJpaEntity entity);
    List<ExerciseRoutineJpaEntity> saveAll(List<ExerciseRoutineJpaEntity> entity);
}
