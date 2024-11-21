package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseJpaRepository extends JpaRepository<ExerciseJpaEntity,Long> {
}
