package com.jaejoo.fitdo.domain.exercise.web.res;

import lombok.Data;

import java.util.List;

@Data
public class ReadRoutinesOfUserResponse {
    private Long routineId;
    private String routineName;
    private List<CategoryAndExerciseWithinRoutineDto> categoryAndExercise;
}
