package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyJpaEntity;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyRepository {
    Optional<DailyJpaEntity> findByUserIdAndDate(Long userId, LocalDate date);

    DailyJpaEntity save(DailyJpaEntity entity);
}
