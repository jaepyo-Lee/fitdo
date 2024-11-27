package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.infra.repository.FriendRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.UserRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendJpaEntity;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void applyFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId);
        User friend = userRepository.findById(friendId);
        FriendJpaEntity userToFriend = FriendJpaEntity.apply(UserJpaEntity.from(friend), UserJpaEntity.from(user));
        FriendJpaEntity friendToUser = FriendJpaEntity.apply(UserJpaEntity.from(user), UserJpaEntity.from(friend));
        friendRepository.save(userToFriend);
        friendRepository.save(friendToUser);
        //sse로 알림 user->friend로
    }
}
