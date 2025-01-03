package com.jaejoo.fitdocore.exercise;

import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdocore.exercise.req.RecordExerciseRecords;
import com.jaejoo.fitdomysql.domain.exercise.core.Exercise;
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

import java.util.ArrayList;
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
        recordCommandRepository.deleteDateRecordOf(userId, command.getRecordDate());
        List<RecordExerciseRecords> records = command.getRecords();
        for (RecordExerciseRecords record : records) {
            Exercise domain = record.toDomain();
            User user = userRepository.findById(userId);
            DailyRecordJpaEntity dailyRecordJpaEntity = dailyRecordRepository.findByUserIdAndDate(userId, command.getRecordDate())
                    .orElseGet(() -> dailyRecordRepository.save(new DailyRecordJpaEntity(command.getRecordDate(), UserJpaEntity.from(user))));
            ExerciseJpaEntity exercise = exerciseQueryRepository.findById(record.exerciseId());
            List<DailyExerciseRecordJpaEntity> exerciseRecordJpaEntities = new ArrayList<>();
            for (ExerciseRecord exerciseRecord : domain.getExerciseRecords()) {
                exerciseRecordJpaEntities.add(DailyExerciseRecordJpaEntity.from(exerciseRecord, dailyRecordJpaEntity, exercise));
            }
            exerciseRecordCommandRepository.saveAll(exerciseRecordJpaEntities);
        }
        return true;
    }
}
