package com.jaejoo.fitdomysql.domain.exercise.infra.repository.projectiondto;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExerciseAndRecordDto {
    private ExerciseJpaEntity exerciseJpaEntity;
    private DailyExerciseRecordJpaEntity dailyExerciseRecordJpaEntity;
}
