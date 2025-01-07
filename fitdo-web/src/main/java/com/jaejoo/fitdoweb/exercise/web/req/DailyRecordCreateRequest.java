package com.jaejoo.fitdoweb.exercise.web.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyRecordCreateRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private List<DailyExerciseRecordsRequest> records;
}
