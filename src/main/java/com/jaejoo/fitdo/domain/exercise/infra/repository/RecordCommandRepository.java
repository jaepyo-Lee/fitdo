package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.domain.exercise.core.ExerciseRecords;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RecordCommandRepository {
    void deleteDateRecordOf(Long userId, Long exerciseId, LocalDate deleteDate);

    void saveAll(Long userId, Long exerciseId, LocalDate dailyDate, ExerciseRecords exerciseRecords);
}
