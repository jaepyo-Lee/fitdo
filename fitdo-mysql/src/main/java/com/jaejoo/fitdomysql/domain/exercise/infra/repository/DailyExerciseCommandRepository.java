package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;

import java.time.LocalDate;
import java.util.List;

public interface DailyExerciseCommandRepository {
    List<DailyExerciseJpaEntity> saveAll(List<DailyExerciseJpaEntity> entities);

    DailyExerciseJpaEntity save(DailyExerciseJpaEntity dailyExercise);

    void deleteDateRecordOf(Long userId, LocalDate deleteDate);
}
