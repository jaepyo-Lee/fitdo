package com.jaejoo.fitdoweb.exercise.web.res;

import lombok.Data;

@Data
public class CategoryAndExerciseWithinRoutineDto {
    private Long categoryId;
    private String categoryName;
    private Long exerciseId;
    private String exerciseName;
}
