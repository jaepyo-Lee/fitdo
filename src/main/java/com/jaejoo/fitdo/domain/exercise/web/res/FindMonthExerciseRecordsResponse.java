package com.jaejoo.fitdo.domain.exercise.web.res;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FindMonthExerciseRecordsResponse {
    private LocalDate exerciseDate;
    private List<FindDateExerciseRecordsResponseDto> dateRecords;
}
