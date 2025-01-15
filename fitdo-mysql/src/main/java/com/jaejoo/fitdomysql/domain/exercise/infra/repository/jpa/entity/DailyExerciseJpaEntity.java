package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 특정 운동 기록
 */
@Getter
@NoArgsConstructor
@Entity
public class DailyExerciseJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_id")
    private DailyJpaEntity daily;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private ExerciseJpaEntity exercise;

    @Builder
    public DailyExerciseJpaEntity(DailyJpaEntity daily, ExerciseJpaEntity exercise) {
        this.daily = daily;
        this.exercise = exercise;
    }

    public static DailyExerciseJpaEntity from(DailyJpaEntity dailyJpaEntity, ExerciseJpaEntity exerciseJpaEntity) {
        return DailyExerciseJpaEntity.builder()
                .daily(dailyJpaEntity)
                .exercise(exerciseJpaEntity)
                .build();
    }
}
