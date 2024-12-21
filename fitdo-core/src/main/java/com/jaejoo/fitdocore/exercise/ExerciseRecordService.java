package com.jaejoo.fitdocore.exercise;

import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdocore.exercise.req.RecordExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindDateExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindMonthExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.ProgressPercentage;
import com.jaejoo.fitdomysql.domain.exercise.core.Exercise;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRecordQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RecordCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RecordQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
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
    private final RecordCommandRepository recordCommandRepository;
    private final ExerciseRecordQueryRepository exerciseRecordQueryRepository;

    @Transactional
    public boolean writeDailyExerciseFrom(Long userId, DailyExerciseRecordCreateCommand command) {
        recordCommandRepository.deleteDateRecordOf(userId, command.getRecordDate());
        List<RecordExerciseRecords> records = command.getRecords();
        for (RecordExerciseRecords record : records) {
            Exercise domain = record.toDomain();
            recordCommandRepository.saveAll(userId, record.getExerciseId(), command.getRecordDate(), domain);
        }
        return true;
    }

    public List<ProgressPercentage> calculateProgressPercentageInMonth(Long userId, YearMonth yearMonth) {
        //해당 연월의 앞뒤 6일까지 조회하기
        List<ProgressInDateDto> allProgressInMonthOfUser = exerciseRecordQueryRepository.findAllProgressInMonthOfUser(userId, yearMonth);
        Map<LocalDate, List<Boolean>> map = new HashMap<>();
        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            map.putIfAbsent(yearMonth.atDay(i), new ArrayList<>());
        }
        for (ProgressInDateDto progressInDateDto : allProgressInMonthOfUser) {
            LocalDate date = progressInDateDto.getDate();
            map.get(date).add(progressInDateDto.getIsProgress());
        }
        List<ProgressPercentage> answer = new ArrayList<>();
        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            List<Boolean> booleans = map.get(yearMonth.atDay(i));
            Long trueCount = booleans.stream().filter(value -> value.equals(Boolean.TRUE)).count();
            Double percentage = ((double) trueCount / (double) booleans.size()) * 100;
            answer.add(new ProgressPercentage(yearMonth.atDay(i), percentage));
        }
        return answer;
    }

    @Transactional(readOnly = true)
    public FindMonthExerciseRecords findExerciseRecordsOfUserAtDate(Long userId, LocalDate date) {
        List<ExerciseAndRecordDto> exerciseAndRecord = exerciseRecordQueryRepository.findExerciseAndRecord(userId, date);

        Map<Long, FindDateExerciseRecords> groupedRecords = groupRecordsByExercise(exerciseAndRecord);
        List<FindDateExerciseRecords> records = new ArrayList<>(groupedRecords.values());

        return FindMonthExerciseRecords.builder()
                .dateRecords(records)
                .exerciseDate(date)
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
            findDateExerciseRecord.getRecords().add(new FindExerciseRecords(
                    dailyRecord.getWeight(),
                    dailyRecord.getVolume(),
                    dailyRecord.getExerciseSet(),
                    dailyRecord.isProgress()
            ));
        }

        return groupedRecords;
    }
}
