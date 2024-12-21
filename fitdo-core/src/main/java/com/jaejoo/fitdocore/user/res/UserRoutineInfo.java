package com.jaejoo.fitdocore.user.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserRoutineInfo {
    private String routineName;
    private List<ExerciseInfoInRoutine> exercises;
}
