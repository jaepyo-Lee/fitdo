package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyRecordJpaRepository extends JpaRepository<DailyRecordJpaEntity,Long> {
    Optional<DailyRecordJpaEntity> findByUserIdAndDate(Long userId, LocalDate date);
}
