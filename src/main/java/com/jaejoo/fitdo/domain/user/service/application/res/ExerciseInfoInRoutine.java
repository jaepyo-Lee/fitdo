package com.jaejoo.fitdo.domain.user.service.application.res;

import com.jaejoo.fitdo.domain.exercise.core.BodyPart;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExerciseInfoInRoutine {
    private String bodyPart;
    private String exerciseName;
}
