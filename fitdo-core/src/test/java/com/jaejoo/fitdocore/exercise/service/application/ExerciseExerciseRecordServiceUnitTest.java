package com.jaejoo.fitdocore.exercise.service.application;

import com.jaejoo.fitdocore.exercise.ExerciseRecordService;
import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordDto;
import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdocore.exercise.req.RecordExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.ProgressPercentage;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRecordQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RecordCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseExerciseRecordServiceUnitTest {
    @InjectMocks
    private ExerciseRecordService exerciseRecordService;
    @Mock
    private RecordCommandRepository recordCommandRepository;
    @Mock
    private ExerciseRecordQueryRepository exerciseRecordQueryRepository;

    @Nested
    @DisplayName("운동기록기능")
    class recordDailyExerciseTest {
        @Test
        void 요청된운동분류마다삭제및저장반복() {
            // given
            RecordExerciseRecords recordExerciseRecords = new RecordExerciseRecords(1L, List.of(new DailyExerciseRecordDto(1, 10, 10, false)));
            RecordExerciseRecords recordExerciseRecords1 = new RecordExerciseRecords(2L, List.of(new DailyExerciseRecordDto(2, 10, 10, false)));

            DailyExerciseRecordCreateCommand command1 = new DailyExerciseRecordCreateCommand(LocalDate.now(), List.of(recordExerciseRecords, recordExerciseRecords1));

            // when
            boolean actual = exerciseRecordService.writeDailyExerciseFrom(1L, command1);

            // then
            System.out.println("=====Logic Start=====");
            System.out.println("=====Logic End=====");

            // 매처를 모두 사용하여 verify 수정
            verify(recordCommandRepository, times(2)).saveAll(any(), any(), any(), any());
            verify(recordCommandRepository, times(1)).deleteDateRecordOf(any(), any());
            assertThat(actual).isTrue();
        }
    }
    @Nested
    @DisplayName("calculateProgressPercentageInMonthTest")
    class calculateProgressPercentageInMonthTest{
        @Test
        void 전체날이31일인경우() {
            // given
            List<ProgressInDateDto> returnValue = new ArrayList<>();
            when(exerciseRecordQueryRepository.findAllProgressInMonthOfUser(anyLong(), any())).thenReturn(returnValue);
            YearMonth yearMonth = YearMonth.of(2024, 12);
            int monthsize = yearMonth.lengthOfMonth();
            // when
            System.out.println("=====Logic Start=====");

            List<ProgressPercentage> progressPercentages = exerciseRecordService.calculateProgressPercentageInMonth(1L, yearMonth);

            System.out.println("=====Logic End=====");
            // then
            assertThat(progressPercentages.size()).isEqualTo(monthsize);
        }

        @Test
        void 전체날이30일인경우() {
            // given
            List<ProgressInDateDto> returnValue = new ArrayList<>();
            when(exerciseRecordQueryRepository.findAllProgressInMonthOfUser(anyLong(), any())).thenReturn(returnValue);
            YearMonth yearMonth = YearMonth.of(2024, 11);
            int monthsize = yearMonth.lengthOfMonth();
            // when
            System.out.println("=====Logic Start=====");

            List<ProgressPercentage> progressPercentages = exerciseRecordService.calculateProgressPercentageInMonth(1L, yearMonth);

            System.out.println("=====Logic End=====");
            // then
            assertThat(progressPercentages.size()).isEqualTo(30);
        }

        @Test
        void 전체날이28일인경우() {
            // given
            List<ProgressInDateDto> returnValue = new ArrayList<>();
            when(exerciseRecordQueryRepository.findAllProgressInMonthOfUser(anyLong(), any())).thenReturn(returnValue);
            YearMonth yearMonth = YearMonth.of(2024, 2);
            // when
            System.out.println("=====Logic Start=====");

            List<ProgressPercentage> progressPercentages = exerciseRecordService.calculateProgressPercentageInMonth(1L, yearMonth);

            System.out.println("=====Logic End=====");
            // then
            assertThat(progressPercentages.size()).isEqualTo(29);
        }
    }

}