package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyRecordJpaRepository extends JpaRepository<DailyRecordJpaEntity,Long> {
    Optional<DailyRecordJpaEntity> findByUserIdAndDate(Long userId,LocalDate date);
}
