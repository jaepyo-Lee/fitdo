package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.projectiondto.ExerciseAndRecordDto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface ExerciseRecordQueryRepository {
    List<ProgressInDateDto> findAllProgressInMonthOfUser(Long userId, YearMonth yearMonth);

    List<DailyExerciseRecordJpaEntity> findExerciseRecordAtDate(Long userId, LocalDate date);

    List<ExerciseAndRecordDto> findExerciseAndRecord(Long userId, LocalDate date);
}
