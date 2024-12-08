package com.jaejoo.fitdo.domain.exercise.service.application.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class FindDateExerciseRecords {
    private Long exerciseId;
    private String exerciseName;
    private String categoryName;
    private List<FindExerciseRecords> records;
}
