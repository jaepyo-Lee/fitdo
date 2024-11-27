package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.FriendJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendServiceTest {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    FriendService friendService;

    @Autowired
    FriendJpaRepository friendJpaRepository;

    @Test
    void 친구추가시_양쪽다친구로_등록() {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(user);

        // when
        System.out.println("=====Logic Start=====");

        friendService.registerFriend(saveUser.getId(), saveFriend.getId());

        System.out.println("=====Logic End=====");
        // then
        assertThat(friendJpaRepository.findAll().size()).isEqualTo(2);
    }
}