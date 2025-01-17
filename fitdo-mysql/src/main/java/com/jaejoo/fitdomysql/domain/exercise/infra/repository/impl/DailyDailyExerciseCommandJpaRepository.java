package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.DailyExerciseCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.DailyExerciseJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DailyDailyExerciseCommandJpaRepository implements DailyExerciseCommandRepository {
    private final DailyExerciseJpaRepository dailyExerciseJpaRepository;

    @Override
    public List<DailyExerciseJpaEntity> saveAll(List<DailyExerciseJpaEntity> entities) {
        return dailyExerciseJpaRepository.saveAll(entities);
    }

    @Override
    public DailyExerciseJpaEntity save(DailyExerciseJpaEntity dailyExercise) {
        return dailyExerciseJpaRepository.save(dailyExercise);
    }
    @Override
    public void deleteDateRecordOf(Long userId, LocalDate deleteDate) {
        dailyExerciseJpaRepository.deleteAllOfUserExerciseRecordsOnDate(userId, deleteDate);
    }
}
