package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyRecordRepository {
    Optional<DailyRecordJpaEntity> findByUserIdAndDate(Long userId, LocalDate date);

    DailyRecordJpaEntity save(DailyRecordJpaEntity entity);
}
