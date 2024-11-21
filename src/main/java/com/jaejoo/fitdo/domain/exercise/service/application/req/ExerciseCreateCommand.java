package com.jaejoo.fitdo.domain.exercise.service.application.req;

import lombok.Getter;

@Getter
public class ExerciseCreateCommand {
    private Long categoryId;
    private String exerciseName;

    public ExerciseCreateCommand(Long categoryId, String exerciseName) {
        this.categoryId = categoryId;
        this.exerciseName = exerciseName;
    }
}
