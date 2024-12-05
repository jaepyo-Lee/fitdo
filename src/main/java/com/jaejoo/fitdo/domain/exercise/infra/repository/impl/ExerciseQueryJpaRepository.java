package com.jaejoo.fitdo.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdo.domain.exercise.infra.repository.ExerciseQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExerciseQueryJpaRepository implements ExerciseQueryRepository {
    private final ExerciseJpaRepository repository;

    @Override
    public List<ExerciseJpaEntity> findExercisesByCategoryAndUserId(CategoryJpaEntity category, Long userId) {
        return repository.findAllByCategoryAndAndUserId(category, userId);
    }

    @Override
    public ExerciseJpaEntity findById(Long exerciseId) {
        return repository.findById(exerciseId).orElseThrow(() -> new IllegalArgumentException("not found entity"));
    }

    @Override
    public List<ExerciseJpaEntity> findAllByIds(List<Long> exerciseIds) {
        return repository.findAllById(exerciseIds);
    }
}
