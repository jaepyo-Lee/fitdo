package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.DailyRecordRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.DailyRecordJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DailyRecordQueryJpaRepository implements DailyRecordRepository {
    private final DailyRecordJpaRepository dailyRecordJpaRepository;
    public Optional<DailyRecordJpaEntity> findByUserIdAndDate(Long userId, LocalDate date){
        return dailyRecordJpaRepository.findByUserIdAndDate(userId, date);
    }

    @Override
    public DailyRecordJpaEntity save(DailyRecordJpaEntity entity) {
        return dailyRecordJpaRepository.save(entity);
    }
}
