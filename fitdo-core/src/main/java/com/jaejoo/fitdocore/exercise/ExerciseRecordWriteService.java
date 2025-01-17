package com.jaejoo.fitdocore.exercise;

import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdocore.exercise.req.RecordExerciseRecords;
import com.jaejoo.fitdomysql.domain.exercise.core.ExerciseRecord;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.*;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseSetJpaEntity;
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
    private final DailyExerciseCommandRepository dailyExerciseCommandRepository;
    private final DailyRepository dailyRepository;
    private final UserRepository userRepository;
    private final ExerciseQueryRepository exerciseQueryRepository;
    private final ExerciseSetCommandRepository exerciseSetCommandRepository;
    private final RecordQueryRepository recordQueryRepository;

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

        DailyJpaEntity dailyJpaEntity = dailyRepository.findByUserIdAndDate(userId, command.getDate())
                .orElseGet(() -> dailyRepository.save(new DailyJpaEntity(command.getDate(), UserJpaEntity.from(user))));

        ExerciseJpaEntity exercise = exerciseQueryRepository.findById(recordsOfEachExercise.exerciseId());

        DailyExerciseJpaEntity dailyExercise = DailyExerciseJpaEntity.from(dailyJpaEntity, exercise);
        DailyExerciseJpaEntity saveDailyExercise = dailyExerciseCommandRepository.save(dailyExercise);

        List<ExerciseSetJpaEntity> list = exerciseRecords.stream().map(
                er -> ExerciseSetJpaEntity.builder()
                        .dailyExercise(saveDailyExercise)
                        .done(er.isProgress())
                        .number(er.set())
                        .weight(er.weight())
                        .volume(er.count())
                        .build()
        ).toList();

        exerciseSetCommandRepository.saveAll(list);
    }

    private void removeOriginRecord(Long userId, DailyExerciseRecordCreateCommand command) {
        List<DailyExerciseJpaEntity> dailyExerciseJpaEntities = recordQueryRepository.findAllByUserIdAndDate(userId, command.getDate());

        for (DailyExerciseJpaEntity dailyExerciseJpaEntity : dailyExerciseJpaEntities) {
            exerciseSetCommandRepository.deleteAllByDailyExercise(dailyExerciseJpaEntity.getId());
        }
        dailyExerciseCommandRepository.deleteDateRecordOf(userId, command.getDate());
    }

}
