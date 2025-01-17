package com.jaejoo.fitdocore.exercise;

import com.jaejoo.fitdocore.exercise.res.FindDateExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindMonthExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.ProgressPercentage;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseSetQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseSetJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ExerciseRecordService {
    private final ExerciseSetQueryRepository exerciseSetQueryRepository;

    @Transactional(readOnly = true)
    public List<ProgressPercentage> readProgressPercentage(Long userId, YearMonth yearMonth) {
        List<ProgressInDateDto> progressesInMonth = exerciseSetQueryRepository.findAllProgress(userId, yearMonth);

        Map<LocalDate, List<Boolean>> progressMap = new HashMap<>();

        progressesInMonth.forEach(progress ->
                progressMap.computeIfAbsent(progress.getDate(),
                        (element) -> new ArrayList<>()).add(progress.getIsProgress())
        );

        return getPercentagesIn(yearMonth, progressMap);
    }

    private static List<ProgressPercentage> getPercentagesIn(YearMonth yearMonth, Map<LocalDate, List<Boolean>> map) {
        final int INIT_DAY = 1;
        final int END_DAY = yearMonth.lengthOfMonth();
        List<ProgressPercentage> answer = new ArrayList<>();

        for (int day = INIT_DAY; day <= END_DAY; day++) {
            LocalDate date = yearMonth.atDay(day);
            Double percent = calculatePercentOfEachDay(map.getOrDefault(date, null));
            answer.add(new ProgressPercentage(date, percent));
        }

        return answer;
    }


    private static Double calculatePercentOfEachDay(List<Boolean> progresses) {
        if (progresses == null) {
            return null;
        }
        long trueCount = progresses.stream().filter(value -> value.equals(Boolean.TRUE)).count();
        return progresses.isEmpty() ? 0 : ((double) trueCount / progresses.size()) * 100;
    }


    @Transactional(readOnly = true)
    public FindMonthExerciseRecords findExerciseRecordsOfUserAtDate(Long userId, LocalDate date) {
        List<DailyExerciseJpaEntity> dailyExerciseJpaEntities = exerciseSetQueryRepository.findExerciseAndRecord(userId, date);
        Map<Long, FindDateExerciseRecords> groupedRecords = new LinkedHashMap<>();
        for (DailyExerciseJpaEntity dailyExerciseJpaEntity : dailyExerciseJpaEntities) {
            ExerciseJpaEntity exercise = dailyExerciseJpaEntity.getExercise();
            List<ExerciseSetJpaEntity> exerciseSets = exerciseSetQueryRepository.findAllByDailyExercise(dailyExerciseJpaEntity);
            // 그룹화된 데이터가 없으면 새로 생성
            groupedRecords.computeIfAbsent(exercise.getId(), id -> new FindDateExerciseRecords(
                    exercise.getId(),
                    exercise.getName(),
                    exercise.getCategory().getPartName(),
                    new ArrayList<>()
            ));

            // 기존 그룹에 데이터 추가
            FindDateExerciseRecords findDateExerciseRecord = groupedRecords.get(exercise.getId());
            exerciseSets.forEach(s -> {
                List<FindExerciseRecords> sets = findDateExerciseRecord.getSets();
                sets.add(new FindExerciseRecords(
                        s.getWeight(),
                        s.getVolume(),
                        s.getNumber(),
                        s.isDone()
                ));
            });
        }

        List<FindDateExerciseRecords> records = new ArrayList<>(groupedRecords.values());

        return FindMonthExerciseRecords.builder()
                .records(records)
                .date(date)
                .build();
    }
}
