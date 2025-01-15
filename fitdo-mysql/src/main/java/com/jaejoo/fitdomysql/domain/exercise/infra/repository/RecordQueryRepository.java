package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public interface RecordQueryRepository {
    List<DailyJpaEntity> findDailyRecordsByUserAndYearMonth(Long userId, YearMonth yearMonth);

    List<DailyExerciseJpaEntity> findExercisesByDailyRecord(DailyJpaEntity dailyRecord);

    List<DailyExerciseJpaEntity> findExerciseRecordsInDailyRecordDividedBy(ExerciseJpaEntity exercise, DailyJpaEntity dailyRecord);

    List<DailyExerciseJpaEntity> findAllByUserIdAndDate(long userId, LocalDate dateTime);
}
