package com.jaejoo.fitdoweb.exercise.web.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FindMonthExerciseRecordsResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate exerciseDate;
    private List<FindDateExerciseRecordsResponseDto> dateRecords;
}
