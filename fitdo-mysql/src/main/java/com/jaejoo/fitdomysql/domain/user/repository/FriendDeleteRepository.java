package com.jaejoo.fitdomysql.domain.user.repository;

public interface FriendDeleteRepository {
    void deleteByReceiverToSender(Long receiverId, Long senderId);
}
