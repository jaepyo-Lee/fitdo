package com.jaejoo.fitdoweb.exercise.web.req;

import com.jaejoo.fitdocore.exercise.req.ExerciseCreateCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ExerciseCreateRequest {
    private Long categoryId;
    private String exerciseName;

    public ExerciseCreateCommand toCommand(Long userId) {
        return new ExerciseCreateCommand(categoryId, exerciseName,userId);
    }
}
