package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;

import java.util.List;

public interface ExerciseRoutineQueryRepository {
    List<ExerciseRoutineJpaEntity>findAllByRoutine(RoutineJpaEntity routine);
}
