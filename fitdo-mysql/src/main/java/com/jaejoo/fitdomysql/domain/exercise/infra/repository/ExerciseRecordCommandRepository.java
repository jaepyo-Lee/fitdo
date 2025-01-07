package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;

import java.util.List;

public interface ExerciseRecordCommandRepository {
    List<DailyExerciseRecordJpaEntity> saveAll(List<DailyExerciseRecordJpaEntity>entities);
}
