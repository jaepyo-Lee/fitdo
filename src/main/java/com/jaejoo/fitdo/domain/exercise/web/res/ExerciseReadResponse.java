package com.jaejoo.fitdo.domain.exercise.web.res;

import com.jaejoo.fitdo.domain.exercise.service.application.res.ExercisesWithinCategory;
import lombok.Data;

import java.util.List;

@Data
public class ExerciseReadResponse {
    private Long categoryId;
    private String categoryName;
    private List<ExercisesWithinCategoryDto> exercises;
}
