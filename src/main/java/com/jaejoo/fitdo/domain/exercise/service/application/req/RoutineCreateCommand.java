package com.jaejoo.fitdo.domain.exercise.service.application.req;

import com.jaejoo.fitdo.domain.exercise.web.req.RoutineCreateRequest;
import lombok.Getter;

import java.util.List;

public record RoutineCreateCommand(Long userId, String name, List<Long> exerciseIds) {

    public static RoutineCreateCommand from(Long userId, RoutineCreateRequest request) {
        return new RoutineCreateCommand(userId, request.getName(), request.getExerciseIds());
    }
}
