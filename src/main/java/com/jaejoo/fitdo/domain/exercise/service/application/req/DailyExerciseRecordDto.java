package com.jaejoo.fitdo.domain.exercise.service.application.req;

import lombok.Getter;

@Getter
public class DailyExerciseRecordDto {
    private final int set;
    private final int weight;
    private final int count;
    private final boolean progress;

    public DailyExerciseRecordDto(int set, int weight, int count, boolean progress) {
        this.set = set;
        this.weight = weight;
        this.count = count;
        this.progress = progress;
    }
}
