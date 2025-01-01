package com.jaejoo.fitdocore.exercise.req;

import lombok.Getter;

public record ExerciseCreateCommand(Long categoryId, String exerciseName, Long userId) {
}
