package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRoutineQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.ExerciseRoutineJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseRoutineRepositoryImpl implements ExerciseRoutineQueryRepository {
    private final ExerciseRoutineJpaRepository repository;

    @Override
    public List<ExerciseRoutineJpaEntity> findAllByRoutine(RoutineJpaEntity routine) {
        return repository.findAllByRoutine(routine);
    }
}
