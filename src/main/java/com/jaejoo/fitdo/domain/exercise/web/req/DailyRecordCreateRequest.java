package com.jaejoo.fitdo.domain.exercise.web.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DailyRecordCreateRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate recordDate;
    private List<DailyExerciseRecordsRequest> dailyExerciseRecords;
}
