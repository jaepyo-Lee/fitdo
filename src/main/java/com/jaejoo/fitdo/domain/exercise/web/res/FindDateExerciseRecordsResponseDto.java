package com.jaejoo.fitdo.domain.exercise.web.res;

import lombok.Data;

import java.util.List;

@Data
public class FindDateExerciseRecordsResponseDto {
    private Long exerciseId;
    private String categoryName;
    private List<FindExerciseRecordsResponseDto> records;
}
