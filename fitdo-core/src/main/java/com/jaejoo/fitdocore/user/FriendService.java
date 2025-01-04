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

        // 사용자의 루틴 목록을 가져오기
        List<RoutineJpaEntity> routines = routineRepository.findAllByUserId(user.getUserId());
        List<UserRoutineInfo> routineDetails = findFriendRoutineDetails(routines);

        return new FriendDetailInfo(
                user.getUserId(),
                user.getNickname(),
                user.getWeight(),
                user.getHeight(),
                routineDetails
        );
    }

    private List<UserRoutineInfo> findFriendRoutineDetails(List<RoutineJpaEntity> routines) {
        List<UserRoutineInfo> routineInfos = new ArrayList<>();

        for (RoutineJpaEntity routine : routines) {
            UserRoutineInfo routineInfo = findExercisesOf(routine);
            routineInfos.add(routineInfo);
        }

        return routineInfos;
    }

    private UserRoutineInfo findExercisesOf(RoutineJpaEntity routine) {
        List<ExerciseRoutineJpaEntity> exerciseInRoutines = exerciseRoutineQueryRepository.findAllByRoutine(routine);
        List<ExerciseInfoInRoutine> exerciseInfoInRoutines = new ArrayList<>();

        for (ExerciseRoutineJpaEntity exerciseRoutine : exerciseInRoutines) {
            ExerciseInfoInRoutine exerciseInfo = findExerciseDetails(exerciseRoutine);
            exerciseInfoInRoutines.add(exerciseInfo);
        }

        return new UserRoutineInfo(routine.getName(), exerciseInfoInRoutines);
    }

    private ExerciseInfoInRoutine findExerciseDetails(ExerciseRoutineJpaEntity exerciseRoutine) {
        ExerciseJpaEntity exercise = exerciseRoutine.getExercise();
        return new ExerciseInfoInRoutine(
                exercise.getCategory().getPartName(),
                exercise.getName()
        );
    }



    @Transactional(readOnly = true)
    public List<FriendSimpleInfo> readFriendsInfos(Long userId) {
        List<FriendJpaEntity> friendRelations = friendRepository.findAllBySenderId(userId);
        return friendRelations.stream()
                .map(relations -> {
                    User friend = relations.getReceiver().toUserModel();
                    String tier = tierCalculator.calculate(friend);
                    return new FriendSimpleInfo(friend.getUserId(), friend.getNickname(), tier);
                })
                .toList();
    }
}
