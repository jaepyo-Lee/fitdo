package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import com.jaejoo.fitdomysql.domain.exercise.core.ExerciseRecord;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 특정 운동 기록
*/
@Getter
@NoArgsConstructor
@Entity
public class DailyExerciseRecordJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int weight;
    private int volume;
    private int exerciseSet;
    private boolean isProgress;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_record_id")
    private DailyRecordJpaEntity dailyRecord;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private ExerciseJpaEntity exercise;

    @Builder
    public DailyExerciseRecordJpaEntity(int weight, int volume, int exerciseSet, boolean isProgress, DailyRecordJpaEntity dailyRecord, ExerciseJpaEntity exercise) {
        this.weight = weight;
        this.volume = volume;
        this.exerciseSet = exerciseSet;
        this.isProgress = isProgress;
        this.dailyRecord = dailyRecord;
        this.exercise = exercise;
    }

    public static DailyExerciseRecordJpaEntity from(ExerciseRecord exerciseRecord, DailyRecordJpaEntity dailyRecordJpaEntity, ExerciseJpaEntity exerciseJpaEntity) {
        return DailyExerciseRecordJpaEntity.builder()
                .dailyRecord(dailyRecordJpaEntity)
                .exercise(exerciseJpaEntity)
                .weight(exerciseRecord.weight())
                .exerciseSet(exerciseRecord.set())
                .isProgress(exerciseRecord.isProgress())
                .volume(exerciseRecord.count())
                .build();
    }
}
