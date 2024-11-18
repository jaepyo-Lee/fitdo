package com.jaejoo.fitdo.domain.exercise.core;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExerciseRecords {
    List<ExerciseRecord> exerciseRecords;

    public ExerciseRecords(List<ExerciseRecord> exerciseRecords) {
        this.exerciseRecords = exerciseRecords;
    }

    public ExerciseRecords() {
        this.exerciseRecords = new ArrayList<ExerciseRecord>();
    }

    public void add(ExerciseRecord exerciseRecord) {
        exerciseRecords.add(exerciseRecord);
    }


}
