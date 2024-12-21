package com.jaejoo.fitdocore.exercise.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class FindExerciseRecords {
    private int weight;
    private int volume;
    private int exerciseSet;
    private boolean isProgress;
}
