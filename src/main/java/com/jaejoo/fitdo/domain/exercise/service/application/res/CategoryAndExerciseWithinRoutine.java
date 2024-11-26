package com.jaejoo.fitdo.domain.exercise.service.application.res;

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
