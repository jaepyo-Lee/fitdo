package com.jaejoo.fitdocore.exercise.req;


import java.util.List;

public record RoutineCreateCommand(Long userId, String name, List<Long> exerciseIds) {
}
