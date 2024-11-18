package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity;

import com.jaejoo.fitdo.domain.exercise.core.ExerciseRecord;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;


/**
 * 특정 운동 기록
*/
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
    @ManyToOne
    @JoinColumn(name = "daily_record_id")
    private DailyRecordJpaEntity dailyRecord;

    @ManyToOne
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
