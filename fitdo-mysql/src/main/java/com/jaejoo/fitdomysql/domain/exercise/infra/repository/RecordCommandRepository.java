package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.core.Exercise;

import java.time.LocalDate;

public interface RecordCommandRepository {
    void deleteDateRecordOf(Long userId, LocalDate deleteDate);

    void saveAll(Long userId, Long exerciseId, LocalDate dailyDate, Exercise exercise);
}
