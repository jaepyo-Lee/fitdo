package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.exercise.core.Exercise;
import com.jaejoo.fitdo.domain.exercise.infra.repository.RecordCommandRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.RecordQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdo.domain.exercise.service.application.req.RecordExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindDateExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindMonthExerciseRecords;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseRecordService {
    private final RecordCommandRepository recordCommandRepository;
    private final RecordQueryRepository recordQueryRepository;

    @Transactional
    public boolean writeDailyExerciseFrom(Long userId, DailyExerciseRecordCreateCommand command) {
        recordCommandRepository.deleteDateRecordOf(userId,command.getRecordDate());
        List<RecordExerciseRecords> records = command.getRecords();
        for (RecordExerciseRecords record : records) {
            Exercise domain = record.toDomain();
            recordCommandRepository.saveAll(userId, record.getExerciseId(), command.getRecordDate(), domain);
        }
        return true;
    }

    @Transactional(readOnly = true)
    public List<FindMonthExerciseRecords> findExerciseRecordsOfUserInMonth(Long userId, YearMonth yearMonth) {
        List<FindMonthExerciseRecords> monthRecords = new ArrayList<>();

        // 1. 월별 DailyRecord 조회
        List<DailyRecordJpaEntity> dailyRecords = recordQueryRepository.findDailyRecordsByUserAndYearMonth(userId, yearMonth);

        for (DailyRecordJpaEntity dailyRecord : dailyRecords) {
            // 2. DailyRecord에 해당하는 Exercise 조회
            List<ExerciseJpaEntity> exercises = recordQueryRepository.findExercisesByDailyRecord(dailyRecord);
            List<FindDateExerciseRecords> dateRecords = new ArrayList<>();
            for (ExerciseJpaEntity exercise : exercises) {
                // 3. Exercise에 해당하는 DailyExerciseRecord 조회
                List<DailyExerciseRecordJpaEntity> exerciseRecords =
                        recordQueryRepository.findExerciseRecordsInDailyRecordDividedBy(exercise, dailyRecord);

                List<FindExerciseRecords> records = new ArrayList<>();
                for (DailyExerciseRecordJpaEntity exerciseRecord : exerciseRecords) {
                    FindExerciseRecords record = FindExerciseRecords.builder()
                            .exerciseSet(exerciseRecord.getExerciseSet())
                            .weight(exerciseRecord.getWeight())
                            .volume(exerciseRecord.getVolume())
                            .isProgress(exerciseRecord.isProgress())
                            .build();
                    records.add(record);
                }

                FindDateExerciseRecords dateExerciseRecords = FindDateExerciseRecords.builder()
                        .exerciseName(exercise.getName())
                        .exerciseId(exercise.getId())
                        .categoryName(exercise.getCategory().getPartName())
                        .records(records)
                        .build();
                dateRecords.add(dateExerciseRecords);
            }
            FindMonthExerciseRecords monthExerciseRecords = FindMonthExerciseRecords.builder()
                    .dateRecords(dateRecords)
                    .exerciseDate(dailyRecord.getDate())
                    .build();
            monthRecords.add(monthExerciseRecords);
        }
        return monthRecords;
    }
}
