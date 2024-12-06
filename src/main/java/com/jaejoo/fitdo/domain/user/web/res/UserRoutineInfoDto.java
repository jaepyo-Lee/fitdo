package com.jaejoo.fitdo.domain.user.web.res;

import lombok.Data;

import java.util.List;
@Data
public class UserRoutineInfoDto {
    private String name;
    private List<ExerciseInfoInRoutineDto> exercises;
}
