package com.jaejoo.fitdoweb.exercise.web.res;

import lombok.Getter;

@Getter
public class ExerciseCreateResponse {
    private String exerciseName;

    public ExerciseCreateResponse(String exercise) {
        this.exerciseName = exercise;
    }
}
