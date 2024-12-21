package com.jaejoo.fitdocore.user;

import com.jaejoo.fitdocore.user.req.*;
import com.jaejoo.fitdocore.user.res.ExerciseInfoInRoutine;
import com.jaejoo.fitdocore.user.res.FriendDetailInfo;
import com.jaejoo.fitdocore.user.res.UserRoutineInfo;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRoutineQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RoutineRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.user.core.Tier;
import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.FriendDeleteRepository;
import com.jaejoo.fitdomysql.domain.user.repository.FriendRepository;
import com.jaejoo.fitdomysql.domain.user.repository.UserRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendJpaEntity;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendStatus;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final FriendDeleteRepository friendDeleteRepository;
    private final RoutineRepository routineRepository;
    private final ExerciseRoutineQueryRepository exerciseRoutineQueryRepository;
    private final RedisTemplate<String, String> redisTemplate;

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

    public List<FriendSimpleInfo> readFriendsInfos(Long userId) {
        List<FriendJpaEntity> friends = friendRepository.findAllBySenderId(userId);
        List<FriendSimpleInfo> friendSimpleInfos = new ArrayList<>();
        Integer totalUserSize = userRepository.findAllSize();
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        for (FriendJpaEntity friend : friends) {
            User user = friend.getReceiver().toUserModel();
            Long rank = zSet.rank("userScore", String.valueOf(user.getUserId()));
            if (rank == null) {
                rank = totalUserSize.longValue(); // 기본적으로 최하위로 설정
            }
            rank += 1;
            FriendSimpleInfo friendSimpleInfo = new FriendSimpleInfo(user.getUserId(), user.getNickname(), Tier.calculateTier(totalUserSize, rank).name());
            friendSimpleInfos.add(friendSimpleInfo);
        }
        return friendSimpleInfos;
    }


    @Transactional
    public void applyFriend(FriendApplyCommand command) {
        User user = userRepository.findById(command.senderId());
        User friend = userRepository.findById(command.receiverId());
        FriendJpaEntity userToFriend = FriendJpaEntity.apply(UserJpaEntity.from(friend), UserJpaEntity.from(user));
        friendRepository.save(userToFriend);
//        FriendJpaEntity friendToUser = FriendJpaEntity.apply(UserJpaEntity.from(user), UserJpaEntity.from(friend));
//        friendRepository.save(friendToUser);
        //sse로 알림 user->friend로
        //친구신청받았는지 확인도 해야할듯
    }

    @Transactional
    public void manageFriendApply(FriendApplyConfirmCommand command) {
        if (!command.isAccept()) {
            cancelFriend(command.receiveUserId(), command.sendUserId());
            return;
        }
        approveFriend(command.sendUserId(), command.receiveUserId());
    }

    private void approveFriend(Long sendUserId, Long recieveUserId) {
        User user = userRepository.findById(sendUserId);
        User friend = userRepository.findById(recieveUserId);
        FriendJpaEntity friendApplyEntity = friendRepository.findBySenderAndReceiver(sendUserId, recieveUserId);
        friendApplyEntity.approve();
        friendRepository.save(new FriendJpaEntity(UserJpaEntity.from(friend), UserJpaEntity.from(user), FriendStatus.ACCEPT));
    }

    private void cancelFriend(Long receiverId, Long senderId) {
        friendDeleteRepository.deleteByReceiverToSender(receiverId, senderId);
    }

    public List<ReadApplierInfo> readFriendApplies(Long receiverId) {
        List<FriendJpaEntity> receiveApplies = friendRepository.findAllByReceiverId(receiverId);
        List<ReadApplierInfo> applierInfos = new ArrayList<>();
        for (FriendJpaEntity receiveApply : receiveApplies) {
            UserJpaEntity sender = receiveApply.getReceiver();
            applierInfos.add(new ReadApplierInfo(sender.getUsername(), sender.getId()));
        }
        return applierInfos;
    }
}
