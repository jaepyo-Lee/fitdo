package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RecordCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.DailyExerciseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DailyRecordCommandJpaRepositoryImpl implements RecordCommandRepository {
    private final DailyExerciseJpaRepository dailyExerciseJpaRepository;

    @Override
    public void deleteDateRecordOf(Long userId, LocalDate deleteDate) {
        dailyExerciseJpaRepository.deleteAllOfUserExerciseRecordsOnDate(userId, deleteDate);
    }
}
