package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.user.core.Tier;
import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.infra.repository.FriendDeleteRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.FriendRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.UserRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendJpaEntity;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendStatus;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendApplyCommand;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendApplyConfirmCommand;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendSimpleInfo;
import com.jaejoo.fitdo.domain.user.service.application.req.ReadApplierInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final FriendDeleteRepository friendDeleteRepository;

    private final RedisTemplate<String, String> redisTemplate;

    public List<FriendSimpleInfo> readFriendInfos(Long userId) {
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
            FriendSimpleInfo friendSimpleInfo = new FriendSimpleInfo(user.getUserId(), user.getNickname(), Tier.calculateTier(totalUserSize, rank));
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
