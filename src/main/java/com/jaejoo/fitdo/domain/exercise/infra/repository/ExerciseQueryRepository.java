package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;

import java.util.List;

public interface ExerciseQueryRepository {
    List<ExerciseJpaEntity> findExercisesByCategoryAndUserId(CategoryJpaEntity category,Long userId);

    ExerciseJpaEntity findById(Long exerciseId);

    List<ExerciseJpaEntity>findAllByIds(List<Long> exerciseIds);
}
