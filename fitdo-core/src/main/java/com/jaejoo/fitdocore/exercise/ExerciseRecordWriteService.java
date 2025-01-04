package com.jaejoo.fitdocore.exercise;

import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdocore.exercise.req.RecordExerciseRecords;
import com.jaejoo.fitdomysql.domain.exercise.core.ExerciseRecord;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.DailyRecordRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRecordCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RecordCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.UserRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseRecordWriteService {
    private final RecordCommandRepository recordCommandRepository;
    private final ExerciseRecordCommandRepository exerciseRecordCommandRepository;
    private final DailyRecordRepository dailyRecordRepository;
    private final UserRepository userRepository;
    private final ExerciseQueryRepository exerciseQueryRepository;

    @Transactional
    public boolean writeDailyExerciseFrom(Long userId, DailyExerciseRecordCreateCommand command) {
        removeOriginRecord(userId, command);

        for (RecordExerciseRecords recordsOfEachExercise : command.getRecords()) {
            saveExerciseRecord(userId, command, recordsOfEachExercise);
        }

        return true;
    }

    private void saveExerciseRecord(Long userId, DailyExerciseRecordCreateCommand command, RecordExerciseRecords recordsOfEachExercise) {
        List<ExerciseRecord> exerciseRecords = recordsOfEachExercise.sets().stream()
                .map(dto -> new ExerciseRecord(dto.weight(), dto.volume(), dto.number(), dto.done()))
                .toList();

        User user = userRepository.findById(userId);

        DailyRecordJpaEntity dailyRecordJpaEntity = dailyRecordRepository.findByUserIdAndDate(userId, command.getDate())
                .orElseGet(() ->
                        dailyRecordRepository.save(new DailyRecordJpaEntity(command.getDate(), UserJpaEntity.from(user))));

        ExerciseJpaEntity exercise = exerciseQueryRepository.findById(recordsOfEachExercise.exerciseId());

        List<DailyExerciseRecordJpaEntity> exerciseRecordJpaEntities = exerciseRecords.stream()
                .map(er -> DailyExerciseRecordJpaEntity.from(er, dailyRecordJpaEntity, exercise))
                .toList();

        exerciseRecordCommandRepository.saveAll(exerciseRecordJpaEntities);
    }

    private void removeOriginRecord(Long userId, DailyExerciseRecordCreateCommand command) {
        recordCommandRepository.deleteDateRecordOf(userId, command.getDate());
    }

}
