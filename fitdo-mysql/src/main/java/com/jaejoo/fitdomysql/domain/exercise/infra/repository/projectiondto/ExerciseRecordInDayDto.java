package com.jaejoo.fitdomysql.domain.exercise.infra.repository.projectiondto;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyJpaEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExerciseRecordInDayDto {
    private final DailyJpaEntity dailyJpaEntity;

    private final ExerciseRecordDto exerciseRecordDto;

    @Builder
    public ExerciseRecordInDayDto(DailyJpaEntity dailyJpaEntity,
                                  ExerciseRecordDto exerciseRecordDto) {
        this.dailyJpaEntity = dailyJpaEntity;
        this.exerciseRecordDto = exerciseRecordDto;
    }
}
