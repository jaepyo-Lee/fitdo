package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.FriendJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendJpaEntity;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendStatus;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendApplyCommand;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendApplyConfirmCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
    void 친구추가시_한쪽만_친구신청상태로_등록() {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(user);

        // when
        System.out.println("=====Logic Start=====");

        friendService.applyFriend(new FriendApplyCommand(saveFriend.getId(), saveUser.getId()));

        System.out.println("=====Logic End=====");
        // then
        assertThat(friendJpaRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void 친구신청시_상태는_친구신청을_보낸사람이_FROM이되어_APPLY상태이어야한다_추가받은사람은_아무엔티티도없다() {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(user);

        // when
        System.out.println("=====Logic Start=====");

        friendService.applyFriend(new FriendApplyCommand(saveFriend.getId(), saveUser.getId()));

        System.out.println("=====Logic End=====");
        // then
        List<FriendJpaEntity> all = friendJpaRepository.findAll();
        int cnt = 0;
        for (FriendJpaEntity friendJpaEntity : all) {
            if (friendJpaEntity.isSupport(FriendStatus.APPLY)) {
                cnt++;
            }
        }
        int finalCnt = cnt;
        assertAll(() -> assertThat(finalCnt).isOne(),
                () -> assertThat(all.size()).isOne());
    }

    @Test
    void 친구신청수락시_요청과응답사용자_모두_친구로_등록되고ACCEPT됩니다() {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(user);

        FriendJpaEntity saveFriendJpaEntity = friendJpaRepository.save(new FriendJpaEntity(saveUser, saveFriend, FriendStatus.APPLY));

        // when
        System.out.println("=====Logic Start=====");

        friendService.manageFriendApply(new FriendApplyConfirmCommand(saveUser.getId(), saveFriend.getId(), true));

        System.out.println("=====Logic End=====");
        // then
        List<FriendJpaEntity> all = friendJpaRepository.findAll();
        int cnt = (int) all.stream().filter(friendJpaEntity -> friendJpaEntity.isSupport(FriendStatus.ACCEPT)).count();
        assertAll(() -> assertThat(all.size()).isEqualTo(2),
                () -> assertThat(cnt).isEqualTo(2));
    }

    @Test
    void 친구신청거절시_목록이_hard_delete됩니다() {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(user);

        FriendJpaEntity saveFriendJpaEntity = friendJpaRepository.save(new FriendJpaEntity(saveUser, saveFriend, FriendStatus.APPLY));

        // when
        System.out.println("=====Logic Start=====");

        friendService.manageFriendApply(new FriendApplyConfirmCommand(saveUser.getId(), saveFriend.getId(), false));

        System.out.println("=====Logic End=====");
        // then
        List<FriendJpaEntity> all = friendJpaRepository.findAll();
        assertAll(() -> assertThat(all.size()).isEqualTo(0));
    }
}