package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExerciseCommandJpaRepository implements ExerciseCommandRepository {
    private final ExerciseJpaRepository exerciseJpaRepository;

    @Override
    public ExerciseJpaEntity save(ExerciseJpaEntity exerciseJpaEntity) {
        return exerciseJpaRepository.save(exerciseJpaEntity);
    }

    @Override
    public void deleteById(Long exerciseId) {
        exerciseJpaRepository.deleteById(exerciseId);
    }
}
