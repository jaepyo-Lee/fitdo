package com.jaejoo.fitdoweb.exercise.web.res;

import lombok.Data;

@Data
public class FindExerciseRecordsResponseDto {
    private int weight;
    private int volume;
    private int number;
    private boolean done;
}
