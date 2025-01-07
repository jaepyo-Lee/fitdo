package com.jaejoo.fitdocore.exercise;

import com.jaejoo.fitdocore.exercise.res.FindDateExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindMonthExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.ProgressPercentage;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRecordQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.projectiondto.ExerciseAndRecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ExerciseRecordService {
    private final ExerciseRecordQueryRepository exerciseRecordQueryRepository;

    @Transactional(readOnly = true)
    public List<ProgressPercentage> readProgressPercentage(Long userId, YearMonth yearMonth) {
        List<ProgressInDateDto> progressesInMonth = exerciseRecordQueryRepository.findAllProgress(userId, yearMonth);

        Map<LocalDate, List<Boolean>> map = new HashMap<>();
        for (ProgressInDateDto dateProgress : progressesInMonth) {
            LocalDate date = dateProgress.getDate();
            map.computeIfAbsent(date, (k) -> new ArrayList<>()).add(dateProgress.getIsProgress());
        }

        return getPercentagesIn(yearMonth, map);
    }

    private static List<ProgressPercentage> getPercentagesIn(YearMonth yearMonth, Map<LocalDate, List<Boolean>> map) {
        final int INIT_DAY = 1;
        List<ProgressPercentage> answer = new ArrayList<>();
        for (int day = INIT_DAY; day <= yearMonth.lengthOfMonth(); day++) {
            double percentage = calculatePercentOfEachDay(yearMonth, map, day);
            answer.add(new ProgressPercentage(yearMonth.atDay(day), percentage));
        }
        return answer;
    }

    private static double calculatePercentOfEachDay(YearMonth yearMonth, Map<LocalDate, List<Boolean>> map, int day) {
        List<Boolean> progresses = map.getOrDefault(yearMonth.atDay(day), new ArrayList<>());
        long trueCount = progresses.stream().filter(value -> value.equals(Boolean.TRUE)).count();
        return progresses.isEmpty() ? 0 : ((double) trueCount / progresses.size()) * 100;
    }


    @Transactional(readOnly = true)
    public FindMonthExerciseRecords findExerciseRecordsOfUserAtDate(Long userId, LocalDate date) {
        List<ExerciseAndRecordDto> exerciseAndRecord = exerciseRecordQueryRepository.findExerciseAndRecord(userId, date);

        Map<Long, FindDateExerciseRecords> groupedRecords = groupRecordsByExercise(exerciseAndRecord);
        List<FindDateExerciseRecords> records = new ArrayList<>(groupedRecords.values());

        return FindMonthExerciseRecords.builder()
                .records(records)
                .date(date)
                .build();
    }

    private Map<Long, FindDateExerciseRecords> groupRecordsByExercise(List<ExerciseAndRecordDto> exerciseAndRecord) {
        Map<Long, FindDateExerciseRecords> groupedRecords = new LinkedHashMap<>();

        for (ExerciseAndRecordDto dto : exerciseAndRecord) {
            ExerciseJpaEntity exercise = dto.getExerciseJpaEntity();
            DailyExerciseRecordJpaEntity dailyRecord = dto.getDailyExerciseRecordJpaEntity();

            // 그룹화된 데이터가 없으면 새로 생성
            groupedRecords.computeIfAbsent(exercise.getId(), id -> new FindDateExerciseRecords(
                    exercise.getId(),
                    exercise.getName(),
                    exercise.getCategory().getPartName(),
                    new ArrayList<>()
            ));

            // 기존 그룹에 데이터 추가
            FindDateExerciseRecords findDateExerciseRecord = groupedRecords.get(exercise.getId());
            findDateExerciseRecord.getSets().add(new FindExerciseRecords(
                    dailyRecord.getWeight(),
                    dailyRecord.getVolume(),
                    dailyRecord.getExerciseSet(),
                    dailyRecord.isProgress()
            ));
        }

        return groupedRecords;
    }
}
