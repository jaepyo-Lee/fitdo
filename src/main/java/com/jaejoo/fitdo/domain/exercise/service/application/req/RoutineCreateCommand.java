package com.jaejoo.fitdo.domain.exercise.service.application.req;

import com.jaejoo.fitdo.domain.exercise.web.req.RoutineCreateRequest;
import lombok.Getter;

import java.util.List;

public record RoutineCreateCommand(String name, List<Long> exerciseIds) {

    public static RoutineCreateCommand from(RoutineCreateRequest request) {
        return new RoutineCreateCommand(request.getName(), request.getExerciseIds());
    }
}
