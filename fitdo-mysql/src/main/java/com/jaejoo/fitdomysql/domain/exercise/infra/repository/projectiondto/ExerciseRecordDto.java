package com.jaejoo.fitdomysql.domain.exercise.infra.repository.projectiondto;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ExerciseRecordDto {
    private final ExerciseJpaEntity exerciseJpaEntity;
    private final List<DailyExerciseJpaEntity> dailyExerciseJpaEntity;
    private final CategoryJpaEntity categoryJpaEntity;

    @Builder
    public ExerciseRecordDto(ExerciseJpaEntity exerciseJpaEntity,
                             List<DailyExerciseJpaEntity> dailyExerciseJpaEntity,
                             CategoryJpaEntity categoryJpaEntity) {
        this.exerciseJpaEntity = exerciseJpaEntity;
        this.dailyExerciseJpaEntity = dailyExerciseJpaEntity;
        this.categoryJpaEntity = categoryJpaEntity;
    }
}
