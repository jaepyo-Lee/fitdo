package com.jaejoo.fitdo.domain.exercise.web.req;

import com.jaejoo.fitdo.domain.exercise.service.application.req.ExerciseCreateCommand;
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

    public ExerciseCreateCommand toCommand() {
        return new ExerciseCreateCommand(categoryId, exerciseName);
    }
}
