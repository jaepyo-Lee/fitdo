package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.dto;

import lombok.Getter;

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
