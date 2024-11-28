package com.jaejoo.fitdo.domain.user.infra.repository.jpa;

import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendJpaRepository extends JpaRepository<FriendJpaEntity, Long> {

    @Query("select F from FriendJpaEntity as F where F.to.id=:receiverId and F.from.id=:senderId")
    Optional<FriendJpaEntity> findBySenderAndReceiver(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    void deleteByToIdAndFromId(Long receiverId, Long senderId);
}
