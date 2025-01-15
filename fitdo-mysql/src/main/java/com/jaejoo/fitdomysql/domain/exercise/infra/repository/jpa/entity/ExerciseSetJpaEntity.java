package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ExerciseSetJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int weight;
    private int volume;
    private int number;
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "daily_exercise_id")
    private DailyExerciseJpaEntity dailyExercise;

    @Builder
    public ExerciseSetJpaEntity(int weight, int volume, int number, boolean done, DailyExerciseJpaEntity dailyExercise) {
        this.weight = weight;
        this.volume = volume;
        this.number = number;
        this.done = done;
        this.dailyExercise = dailyExercise;
    }
}
