package com.jaejoo.fitdoweb.exercise.web.res;

import com.jaejoo.fitdocore.exercise.res.ExercisesWithinCategory;
import lombok.Data;

import java.util.List;

@Data
public class ExerciseReadResponse {
    private Long categoryId;
    private String categoryName;
    private List<ExercisesWithinCategoryDto> exercises;
}
