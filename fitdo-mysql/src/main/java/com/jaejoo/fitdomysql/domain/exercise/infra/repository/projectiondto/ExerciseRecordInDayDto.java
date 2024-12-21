package com.jaejoo.fitdomysql.domain.exercise.infra.repository.projectiondto;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExerciseRecordInDayDto {
    private final DailyRecordJpaEntity dailyRecordJpaEntity;

    private final ExerciseRecordDto exerciseRecordDto;

    @Builder
    public ExerciseRecordInDayDto(DailyRecordJpaEntity dailyRecordJpaEntity,
                                  ExerciseRecordDto exerciseRecordDto) {
        this.dailyRecordJpaEntity = dailyRecordJpaEntity;
        this.exerciseRecordDto = exerciseRecordDto;
    }
}
