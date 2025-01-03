package com.jaejoo.fitdomysql.domain.exercise.core;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Exercise {
    private final List<ExerciseRecord> exerciseRecords;

    public Exercise(List<ExerciseRecord> exerciseRecords) {
        this.exerciseRecords = exerciseRecords;
    }

    public Exercise() {
        this.exerciseRecords = new ArrayList<ExerciseRecord>();
    }

    public void add(ExerciseRecord exerciseRecord) {
        exerciseRecords.add(exerciseRecord);
    }


}
