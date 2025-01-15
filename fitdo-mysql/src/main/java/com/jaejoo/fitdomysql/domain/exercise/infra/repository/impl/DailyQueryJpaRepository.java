package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.DailyRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.DailyJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DailyQueryJpaRepository implements DailyRepository {
    private final DailyJpaRepository dailyJpaRepository;
    public Optional<DailyJpaEntity> findByUserIdAndDate(Long userId, LocalDate date){
        return dailyJpaRepository.findByUserIdAndDate(userId, date);
    }

    @Override
    public DailyJpaEntity save(DailyJpaEntity entity) {
        return dailyJpaRepository.save(entity);
    }
}
