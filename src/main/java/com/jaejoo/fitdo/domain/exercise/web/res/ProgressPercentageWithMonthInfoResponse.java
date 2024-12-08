package com.jaejoo.fitdo.domain.exercise.web.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProgressPercentageWithMonthInfoResponse {
    private int startDayValue;
    private List<ProgressPercentageDto> percentagesInMonth;
}
