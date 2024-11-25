package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ExerciseRoutineJpaRepository extends JpaRepository<ExerciseRoutineJpaEntity,Long> {

}
