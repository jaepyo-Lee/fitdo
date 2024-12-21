package com.jaejoo.fitdocore.exercise.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ReadRoutineOfUser {
    private Long routineId;
    private String routineName;
    private List<CategoryAndExerciseWithinRoutine> categoryAndExercise;
}
