package com.jaejoo.fitdocore.user;

import com.jaejoo.fitdocore.user.req.FriendApplyCommand;
import com.jaejoo.fitdocore.util.AESConverter;
import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.FriendRepository;
import com.jaejoo.fitdomysql.domain.user.repository.UserRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendJpaEntity;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendShareService {
    private final AESConverter aesConverter;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Value("${deeplink.url}")
    private String DEEP_LINK_URL;

    public String generateDeepLink(Long userId) throws Exception {
        String serializeUserId = aesConverter.serialize(String.valueOf(userId));
        return DEEP_LINK_URL + serializeUserId;
    }

    @Transactional(readOnly = true)
    public void applyFriend(FriendApplyCommand command) throws Exception {
        long senderId = Long.parseLong(aesConverter.deserialize(command.senderId()));
        if (senderId == command.receiverId()) {
            throw new IllegalArgumentException("같은 사용자끼리 친구될수 없습니다.");
        }
        User user = userRepository.findById(senderId);
        User friend = userRepository.findById(command.receiverId());
        FriendJpaEntity userToFriend = FriendJpaEntity.apply(UserJpaEntity.from(friend), UserJpaEntity.from(user));
        FriendJpaEntity FriendToUser = FriendJpaEntity.apply(UserJpaEntity.from(user), UserJpaEntity.from(friend));
        friendRepository.save(userToFriend);
        friendRepository.save(FriendToUser);
    }
}
