package com.jaejoo.fitdocore.exercise.req;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Builder
public class DailyExerciseRecordCreateCommand {
    private LocalDate date;
    private List<RecordExerciseRecords> records;

    public DailyExerciseRecordCreateCommand(LocalDate date, List<RecordExerciseRecords> records) {
        this.date = date;
        this.records = records;
    }
}
