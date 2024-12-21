package com.jaejoo.fitdoweb.exercise.web.req.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExerciseRecordRequestDto {
    private int set;
    private int weight;
    private int count;
    private boolean progress;
}
