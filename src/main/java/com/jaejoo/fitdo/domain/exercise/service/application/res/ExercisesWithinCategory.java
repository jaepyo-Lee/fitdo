package com.jaejoo.fitdo.domain.exercise.service.application.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExercisesWithinCategory {
    private String exerciseName;
    private Long exerciseId;
}
