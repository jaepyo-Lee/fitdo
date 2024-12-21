package com.jaejoo.fitdomysql.domain.exercise.infra.repository;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;

import java.util.List;

public interface ExerciseRoutineCommandRepository {
    ExerciseRoutineJpaEntity save(ExerciseRoutineJpaEntity entity);
    List<ExerciseRoutineJpaEntity> saveAll(List<ExerciseRoutineJpaEntity> entity);
    void deleteAllByRoutine(RoutineJpaEntity routine);
    void deleteAllByExercise(ExerciseJpaEntity exercise);
}
