package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseSetJpaEntity;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface ExerciseSetQueryRepository {
    List<ProgressInDateDto> findAllProgress(Long userId, YearMonth yearMonth);

    List<ExerciseSetJpaEntity> findExerciseRecordAtDate(Long userId, LocalDate date);

    List<DailyExerciseJpaEntity> findExerciseAndRecord(Long userId, LocalDate date);

    List<ExerciseSetJpaEntity> findAllByDailyExercise(DailyExerciseJpaEntity dailyExerciseJpaEntity);
}
