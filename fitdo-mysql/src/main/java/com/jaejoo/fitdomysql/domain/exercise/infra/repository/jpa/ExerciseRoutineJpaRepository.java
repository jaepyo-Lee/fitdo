package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRoutineJpaRepository extends JpaRepository<ExerciseRoutineJpaEntity, Long> {
    List<ExerciseRoutineJpaEntity> findAllByRoutine(RoutineJpaEntity routine);

    void deleteAllByRoutine(RoutineJpaEntity routine);

    void deleteAllByExercise(ExerciseJpaEntity exerciseJpaEntity);
}
