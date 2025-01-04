package com.jaejoo.fitdocore.user;

import com.jaejoo.fitdocore.user.impl.TierCalculator;
import com.jaejoo.fitdocore.user.req.FriendSimpleInfo;
import com.jaejoo.fitdocore.user.res.ExerciseInfoInRoutine;
import com.jaejoo.fitdocore.user.res.FriendDetailInfo;
import com.jaejoo.fitdocore.user.res.UserRoutineInfo;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRoutineQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RoutineRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.FriendRepository;
import com.jaejoo.fitdomysql.domain.user.repository.UserRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;
    private final ExerciseRoutineQueryRepository exerciseRoutineQueryRepository;
    private final TierCalculator tierCalculator;

    @Transactional(readOnly = true)
    public FriendDetailInfo readFriendDetailInfo(Long userId) {
        User user = userRepository.findById(userId);
        List<RoutineJpaEntity> routines = routineRepository.findAllByUserId(user.getUserId());
        List<UserRoutineInfo> routineInfos = new ArrayList<>();
        for (RoutineJpaEntity routine : routines) {
            List<ExerciseInfoInRoutine> exerciseInfoInRoutines = new ArrayList<>();
            List<ExerciseRoutineJpaEntity> allByRoutine = exerciseRoutineQueryRepository.findAllByRoutine(routine);
            for (ExerciseRoutineJpaEntity exerciseRoutineJpaEntity : allByRoutine) {
                ExerciseJpaEntity exercise = exerciseRoutineJpaEntity.getExercise();
                exerciseInfoInRoutines.add(new ExerciseInfoInRoutine(exercise.getCategory().getPartName(), exercise.getName()));
            }
            routineInfos.add(new UserRoutineInfo(routine.getName(), exerciseInfoInRoutines));
        }
        return new FriendDetailInfo(user.getUserId(), user.getNickname(), user.getWeight(), user.getHeight(), routineInfos);
    }

    @Transactional(readOnly = true)
    public List<FriendSimpleInfo> readFriendsInfos(Long userId) {
        List<FriendJpaEntity> friendRelations = friendRepository.findAllBySenderId(userId);
        return friendRelations.stream()
                .map(relations -> {
                    User friend = relations.getReceiver().toUserModel();
                    return tierCalculator.calculate(friend);
                })
                .toList();
    }
}
