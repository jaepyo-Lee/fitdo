package com.jaejoo.fitdomysql.domain.user.repository.jpa;


import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findByAuthId(String authId);
    boolean existsByNickname(String nickname);
}
