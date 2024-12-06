package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.exercise.infra.repository.RecordCommandRepository;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyExerciseRecordDto;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdo.domain.exercise.service.application.req.RecordExerciseRecords;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExerciseExerciseRecordServiceUnitTest {
    @InjectMocks
    private ExerciseRecordService exerciseRecordService;
    @Mock
    private RecordCommandRepository recordCommandRepository;

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
}