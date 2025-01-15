package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyJpaRepository extends JpaRepository<DailyJpaEntity,Long> {
    Optional<DailyJpaEntity> findByUserIdAndDate(Long userId, LocalDate date);
}
