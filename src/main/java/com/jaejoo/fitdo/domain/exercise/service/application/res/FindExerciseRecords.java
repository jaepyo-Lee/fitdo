package com.jaejoo.fitdo.domain.exercise.service.application.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindExerciseRecords {
    private int weight;
    private int volume;
    private int exerciseSet;
    private boolean isProgress;
}
