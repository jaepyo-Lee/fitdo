package com.jaejoo.fitdocore.exercise.service.application;

import com.jaejoo.fitdocore.exercise.ExerciseRecordService;
import com.jaejoo.fitdocore.exercise.res.ProgressPercentage;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseSetQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseRecordServiceUnitTest {
    @InjectMocks
    private ExerciseRecordService exerciseRecordService;
    @Mock
    private ExerciseSetQueryRepository exerciseSetQueryRepository;

    @Nested
    @DisplayName("calculateProgressPercentageInMonthTest")
    class calculateProgressPercentageInMonthTest{
        @Test
        void 전체날이31일인경우() {
            // given
            List<ProgressInDateDto> returnValue = new ArrayList<>();
            when(exerciseSetQueryRepository.findAllProgress(anyLong(), any())).thenReturn(returnValue);
            YearMonth yearMonth = YearMonth.of(2024, 12);
            int monthsize = yearMonth.lengthOfMonth();
            // when
            System.out.println("=====Logic Start=====");

            List<ProgressPercentage> progressPercentages = exerciseRecordService.readProgressPercentage(1L, yearMonth);

            System.out.println("=====Logic End=====");
            // then
            assertThat(progressPercentages.size()).isEqualTo(monthsize);
        }

        @Test
        void 전체날이30일인경우() {
            // given
            List<ProgressInDateDto> returnValue = new ArrayList<>();
            when(exerciseSetQueryRepository.findAllProgress(anyLong(), any())).thenReturn(returnValue);
            YearMonth yearMonth = YearMonth.of(2024, 11);
            int monthsize = yearMonth.lengthOfMonth();
            // when
            System.out.println("=====Logic Start=====");

            List<ProgressPercentage> progressPercentages = exerciseRecordService.readProgressPercentage(1L, yearMonth);

            System.out.println("=====Logic End=====");
            // then
            assertThat(progressPercentages.size()).isEqualTo(30);
        }

        @Test
        void 전체날이28일인경우() {
            // given
            List<ProgressInDateDto> returnValue = new ArrayList<>();
            when(exerciseSetQueryRepository.findAllProgress(anyLong(), any())).thenReturn(returnValue);
            YearMonth yearMonth = YearMonth.of(2024, 2);
            // when
            System.out.println("=====Logic Start=====");

            List<ProgressPercentage> progressPercentages = exerciseRecordService.readProgressPercentage(1L, yearMonth);

            System.out.println("=====Logic End=====");
            // then
            assertThat(progressPercentages.size()).isEqualTo(29);
        }
    }

}