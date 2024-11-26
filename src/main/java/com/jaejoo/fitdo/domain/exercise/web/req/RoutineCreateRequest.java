package com.jaejoo.fitdo.domain.exercise.web.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class RoutineCreateRequest {
    private String name;
    private List<Long> exerciseIds;

    public RoutineCreateRequest(String name, List<Long> exerciseIds) {
        this.name = name;
        this.exerciseIds = exerciseIds;
    }
}
