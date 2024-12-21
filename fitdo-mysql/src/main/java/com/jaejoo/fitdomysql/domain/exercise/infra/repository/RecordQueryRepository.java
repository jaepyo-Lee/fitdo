package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;

import java.time.YearMonth;
import java.util.List;

public interface RecordQueryRepository {
    List<DailyRecordJpaEntity> findDailyRecordsByUserAndYearMonth(Long userId, YearMonth yearMonth);

    List<ExerciseJpaEntity> findExercisesByDailyRecord(DailyRecordJpaEntity dailyRecord);

    List<DailyExerciseRecordJpaEntity> findExerciseRecordsInDailyRecordDividedBy(ExerciseJpaEntity exercise, DailyRecordJpaEntity dailyRecord);
}
