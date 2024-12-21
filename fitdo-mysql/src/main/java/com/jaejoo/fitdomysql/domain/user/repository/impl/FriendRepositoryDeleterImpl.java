package com.jaejoo.fitdomysql.domain.user.repository.impl;

import com.jaejoo.fitdomysql.domain.user.repository.FriendDeleteRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.QFriendJpaEntity.friendJpaEntity;

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
