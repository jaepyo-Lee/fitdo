package com.jaejoo.fitdoweb.exercise.web.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({"categoryName,exerciseId,exerciseName,sets"})
public class FindDateExerciseRecordsResponseDto {
    private Long exerciseId;
    private String exerciseName;
    private String categoryName;
    private List<FindExerciseRecordsResponseDto> sets;
}
