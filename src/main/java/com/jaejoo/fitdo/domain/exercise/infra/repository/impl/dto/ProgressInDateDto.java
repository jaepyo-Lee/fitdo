package com.jaejoo.fitdo.domain.exercise.infra.repository.impl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class ProgressInDateDto {
    private LocalDate date;
    private Boolean isProgress;

    public ProgressInDateDto(LocalDate date, Boolean isProgress) {
        this.date = date;
        this.isProgress = isProgress;
    }
}
