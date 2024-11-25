package com.jaejoo.fitdo.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdo.domain.exercise.infra.repository.ExerciseRoutineCommandRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.ExerciseRoutineJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
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
}
