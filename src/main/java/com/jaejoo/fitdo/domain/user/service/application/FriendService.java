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
    public void registerFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId);
        User friend = userRepository.findById(friendId);
        FriendJpaEntity userToFriend = FriendJpaEntity.builder()
                .to(UserJpaEntity.from(friend)).from(UserJpaEntity.from(user))
                .build();
        FriendJpaEntity friendToUser = FriendJpaEntity.builder()
                .to(UserJpaEntity.from(user)).from(UserJpaEntity.from(friend))
                .build();
        friendRepository.save(userToFriend);
        friendRepository.save(friendToUser);
    }

}
