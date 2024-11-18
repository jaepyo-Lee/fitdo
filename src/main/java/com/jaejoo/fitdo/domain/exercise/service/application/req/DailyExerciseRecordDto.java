package com.jaejoo.fitdo.domain.exercise.service.application.req;

import lombok.Getter;

@Getter
public class DailyExerciseRecordDto {
    private int set;
    private int weight;
    private int count;
    private boolean isProgress;

    public DailyExerciseRecordDto(int set, int weight, int count, boolean isProgress) {
        this.set = set;
        this.weight = weight;
        this.count = count;
        this.isProgress = isProgress;
    }
}
