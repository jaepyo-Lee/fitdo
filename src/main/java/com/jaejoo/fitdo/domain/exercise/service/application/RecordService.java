package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.exercise.core.ExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.infra.repository.RecordCommandRepository;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyRecordCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordService {
    private final RecordCommandRepository recordCommandRepository;

    @Transactional
    public boolean writeDailyExerciseFrom(Long userId, List<DailyRecordCreateCommand> commands) {
        for (DailyRecordCreateCommand command : commands) {
            ExerciseRecords domain = command.toDomain();
            recordCommandRepository.deleteDateRecordOf(userId, command.getExerciseId(), command.getRecordDate());
            recordCommandRepository.saveAll(userId, command.getExerciseId(), command.getRecordDate(), domain);
        }
        return true;
    }
}
