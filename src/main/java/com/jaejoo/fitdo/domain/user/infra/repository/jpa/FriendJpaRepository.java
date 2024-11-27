package com.jaejoo.fitdo.domain.user.infra.repository.jpa;

import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendJpaRepository extends JpaRepository<FriendJpaEntity,Long> {

}
