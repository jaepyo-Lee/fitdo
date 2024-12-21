package com.jaejoo.fitdomysql.domain.exercise.infra.repository;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;

public interface ExerciseCommandRepository {
    ExerciseJpaEntity save(ExerciseJpaEntity exerciseJpaEntity);

    void deleteById(Long exerciseId);
}
