package com.jaejoo.fitdocore.exercise.res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class FindMonthExerciseRecords {
    private LocalDate exerciseDate;
    private List<FindDateExerciseRecords> dateRecords;
}
