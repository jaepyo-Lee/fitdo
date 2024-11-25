package com.jaejoo.fitdo.domain.exercise.service.application.req;

import lombok.Getter;

import java.util.List;

@Getter
public class RoutineCreateCommand {
    private String name;
    private List<Long> exerciseIds;

    public RoutineCreateCommand(String name, List<Long> exerciseIds) {
        this.name = name;
        this.exerciseIds = exerciseIds;
    }
}
