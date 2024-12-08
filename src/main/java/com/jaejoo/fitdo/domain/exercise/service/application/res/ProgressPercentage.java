package com.jaejoo.fitdo.domain.exercise.service.application.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ProgressPercentage {
    private LocalDate date;
    private Double percentage;
}
