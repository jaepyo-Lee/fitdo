package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRoutineCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.ExerciseRoutineJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseRoutineCommandJpaRepository implements ExerciseRoutineCommandRepository {
    private final ExerciseRoutineJpaRepository exerciseRoutineJpaRepository;

    @Override
    public List<ExerciseRoutineJpaEntity> saveAll(List<ExerciseRoutineJpaEntity> entity) {
        return exerciseRoutineJpaRepository.saveAll(entity);
    }

    @Override
    public ExerciseRoutineJpaEntity save(ExerciseRoutineJpaEntity entity) {
        return exerciseRoutineJpaRepository.save(entity);
    }

    @Override
    public void deleteAllByRoutine(RoutineJpaEntity routine) {
        exerciseRoutineJpaRepository.deleteAllByRoutine(routine);
    }

    @Override
    public void deleteAllByExercise(ExerciseJpaEntity exercise) {
        exerciseRoutineJpaRepository.deleteAllByExercise(exercise);
    }
}
