package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRecordCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.DailyExerciseRecordJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExerciseRecordCommandJpaRepository implements ExerciseRecordCommandRepository {
    private final DailyExerciseRecordJpaRepository dailyExerciseRecordJpaRepository;

    @Override
    public List<DailyExerciseRecordJpaEntity> saveAll(List<DailyExerciseRecordJpaEntity> entities) {
        return dailyExerciseRecordJpaRepository.saveAll(entities);
    }
}
