package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseJpaRepository extends JpaRepository<ExerciseJpaEntity,Long> {
    List<ExerciseJpaEntity> findAllByCategoryAndUserId(CategoryJpaEntity category, Long userId);
}
