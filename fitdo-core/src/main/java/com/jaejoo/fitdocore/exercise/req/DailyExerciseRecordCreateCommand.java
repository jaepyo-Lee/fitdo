package com.jaejoo.fitdocore.exercise.req;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Builder
public class DailyExerciseRecordCreateCommand {
    private LocalDate recordDate;
    private List<RecordExerciseRecords> records;

    public DailyExerciseRecordCreateCommand(LocalDate recordDate, List<RecordExerciseRecords> records) {
        this.recordDate = recordDate;
        this.records = records;
    }
}
