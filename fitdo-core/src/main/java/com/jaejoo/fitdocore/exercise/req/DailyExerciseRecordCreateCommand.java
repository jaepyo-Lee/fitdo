package com.jaejoo.fitdocore.exercise.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
public class DailyExerciseRecordCreateCommand {
    private LocalDate recordDate;
    private List<RecordExerciseRecords> records;

    public DailyExerciseRecordCreateCommand(LocalDate recordDate, List<RecordExerciseRecords> records) {
        this.recordDate = recordDate;
        this.records = records;
    }
}
