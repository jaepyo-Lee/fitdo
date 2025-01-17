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
        List<DailyExerciseJpaEntity> dailyExercises = exerciseSetQueryRepository.findExerciseAndRecord(userId, date);

        Map<Long, FindDateExerciseRecords> exerciseRecordsMap = mapDailyExercisesToRecords(dailyExercises);

        List<FindDateExerciseRecords> exerciseRecords = new ArrayList<>(exerciseRecordsMap.values());

        return FindMonthExerciseRecords.builder()
                .records(exerciseRecords)
                .date(date)
                .build();
    }

    private Map<Long, FindDateExerciseRecords> mapDailyExercisesToRecords(List<DailyExerciseJpaEntity> dailyExercises) {
        Map<Long, FindDateExerciseRecords> exerciseRecordsMap = new LinkedHashMap<>();

        for (DailyExerciseJpaEntity dailyExercise : dailyExercises) {
            ExerciseJpaEntity exercise = dailyExercise.getExercise();
            Long exerciseId = exercise.getId();

            // 그룹화된 데이터가 없으면 초기화
            exerciseRecordsMap.computeIfAbsent(exerciseId, id -> createExerciseRecord(exercise));

            // 그룹화된 데이터에 세트 추가
            List<ExerciseSetJpaEntity> exerciseSets = exerciseSetQueryRepository.findAllByDailyExercise(dailyExercise);
            appendExerciseSetsToRecord(exerciseRecordsMap.get(exerciseId), exerciseSets);
        }

        return exerciseRecordsMap;
    }

    private FindDateExerciseRecords createExerciseRecord(ExerciseJpaEntity exercise) {
        return new FindDateExerciseRecords(
                exercise.getId(),
                exercise.getName(),
                exercise.getCategory().getPartName(),
                new ArrayList<>()
        );
    }

    private void appendExerciseSetsToRecord(FindDateExerciseRecords exerciseRecord, List<ExerciseSetJpaEntity> exerciseSets) {
        List<FindExerciseRecords> setRecords = exerciseRecord.getSets();

        exerciseSets.forEach(set ->
                setRecords.add(new FindExerciseRecords(
                        set.getWeight(),
                        set.getVolume(),
                        set.getNumber(),
                        set.isDone()
                ))
        );
    }

}
