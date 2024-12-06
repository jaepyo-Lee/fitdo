package com.jaejoo.fitdo.domain.user.infra.repository;

public interface FriendDeleteRepository {
    void deleteByReceiverToSender(Long receiverId, Long senderId);
}
