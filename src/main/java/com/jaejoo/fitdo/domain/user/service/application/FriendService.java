package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.infra.repository.FriendRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.UserRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendJpaEntity;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendStatus;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendApplyCommand;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendApplyConfirmCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

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
            cancelFriend(command.receiveUserId(), command.receiveUserId());
            return;
        }
        approveFriend(command.sendUserId(),command.receiveUserId());
    }

    private void approveFriend(Long sendUserId, Long recieveUserId) {
        User user = userRepository.findById(sendUserId);
        User friend = userRepository.findById(recieveUserId);
        FriendJpaEntity friendApplyEntity = friendRepository.findBySenderAndReceiver(sendUserId, recieveUserId);
        friendApplyEntity.approve();
        friendRepository.save(new FriendJpaEntity(UserJpaEntity.from(friend), UserJpaEntity.from(user), FriendStatus.ACCEPT));
    }

    private void cancelFriend(Long receiverId, Long senderId) {
        friendRepository.deleteByReceiverToSender(receiverId, senderId);
    }
}
