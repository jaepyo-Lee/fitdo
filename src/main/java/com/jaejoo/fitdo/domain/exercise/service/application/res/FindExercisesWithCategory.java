package com.jaejoo.fitdo.domain.exercise.service.application.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FindExercisesWithCategory {
    private Long categoryId;
    private String categoryName;
    private List<ExercisesWithinCategory> exercises;
}
