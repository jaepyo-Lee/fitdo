package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;

import java.time.YearMonth;
import java.util.List;

public interface ExerciseRecordQueryRepository {
    List<ProgressInDateDto> findAllProgressInMonthOfUser(Long userId, YearMonth yearMonth);
}
