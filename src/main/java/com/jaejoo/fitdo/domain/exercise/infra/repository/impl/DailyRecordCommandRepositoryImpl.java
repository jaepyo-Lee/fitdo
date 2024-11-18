package com.jaejoo.fitdo.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdo.domain.exercise.core.ExerciseRecord;
import com.jaejoo.fitdo.domain.exercise.core.ExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.infra.repository.RecordCommandRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.DailyExerciseRecordJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.DailyRecordJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DailyRecordCommandRepositoryImpl implements RecordCommandRepository {
    private final DailyExerciseRecordJpaRepository dailyExerciseRecordJpaRepository;
    private final DailyRecordJpaRepository dailyRecordJpaRepository;
    private final ExerciseJpaRepository exerciseJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void saveAll(Long userId, Long exerciseId, LocalDate dailyDate, ExerciseRecords exerciseRecords) {
        UserJpaEntity user = userJpaRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));
        DailyRecordJpaEntity dailyRecordJpaEntity = dailyRecordJpaRepository.findByUserIdAndDate(userId, dailyDate)
                .orElseGet(() -> dailyRecordJpaRepository.save(new DailyRecordJpaEntity(dailyDate, user)));
        ExerciseJpaEntity exercise = exerciseJpaRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("exercise not found"));
        List<DailyExerciseRecordJpaEntity> exerciseRecordJpaEntities = new ArrayList<>();
        for (ExerciseRecord exerciseRecord : exerciseRecords.getExerciseRecords()) {
            exerciseRecordJpaEntities.add(DailyExerciseRecordJpaEntity.from(exerciseRecord, dailyRecordJpaEntity, exercise));
        }
        dailyExerciseRecordJpaRepository.saveAll(exerciseRecordJpaEntities);
    }

    @Override
    public void deleteDateRecordOf(Long userId, Long exerciseId, LocalDate deleteDate) {
        dailyExerciseRecordJpaRepository.deleteAllOfUserExerciseRecordsOnDate(userId, exerciseId, deleteDate);
    }
}
