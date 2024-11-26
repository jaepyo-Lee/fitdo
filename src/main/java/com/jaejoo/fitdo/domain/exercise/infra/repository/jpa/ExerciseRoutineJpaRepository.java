package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ExerciseRoutineJpaRepository extends JpaRepository<ExerciseRoutineJpaEntity, Long> {
    List<ExerciseRoutineJpaEntity> findAllByRoutine(RoutineJpaEntity routine);
}
