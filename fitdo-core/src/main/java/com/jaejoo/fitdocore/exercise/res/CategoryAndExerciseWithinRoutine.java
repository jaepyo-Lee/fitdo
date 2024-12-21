package com.jaejoo.fitdocore.exercise.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryAndExerciseWithinRoutine {
    private Long categoryId;
    private String categoryName;
    private Long exerciseId;
    private String exerciseName;
}
