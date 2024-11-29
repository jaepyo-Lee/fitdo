package com.jaejoo.fitdo.domain.user.infra.repository.impl;

import com.jaejoo.fitdo.domain.user.infra.repository.FriendDeleteRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.QFriendJpaEntity.friendJpaEntity;

@Repository
@RequiredArgsConstructor
public class FriendRepositoryDeleterImpl implements FriendDeleteRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByReceiverToSender(Long receiverId, Long senderId) {
        queryFactory.delete(friendJpaEntity)
                .where(friendJpaEntity.receiver.id.eq(receiverId))
                .where(friendJpaEntity.sender.id.eq(senderId))
                .execute();
    }
}
