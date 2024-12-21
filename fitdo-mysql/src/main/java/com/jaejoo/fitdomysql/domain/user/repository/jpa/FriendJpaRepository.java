package com.jaejoo.fitdomysql.domain.user.repository.jpa;

import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendJpaRepository extends JpaRepository<FriendJpaEntity, Long> {

    @Query("select F from FriendJpaEntity as F where F.receiver.id=:receiverId and F.sender.id=:senderId")
    Optional<FriendJpaEntity> findBySenderAndReceiver(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    @Query("select F from FriendJpaEntity as F where F.receiver.id=:receiverId and F.friendStatus='APPLY'")
    List<FriendJpaEntity> findAllByToId(@Param(("receiverId")) Long receiverId);

    List<FriendJpaEntity> findAllBySenderId(Long senderId);
}
