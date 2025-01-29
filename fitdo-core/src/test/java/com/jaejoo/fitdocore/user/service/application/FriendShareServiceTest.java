package com.jaejoo.fitdocore.user.service.application;

import com.jaejoo.fitdocore.user.FriendShareService;
import com.jaejoo.fitdocore.user.req.FriendApplyCommand;
import com.jaejoo.fitdocore.util.AESConverter;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.FriendJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendJpaEntity;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendStatus;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class FriendShareServiceTest {
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    FriendShareService friendService;
    @Autowired
    FriendJpaRepository friendJpaRepository;
    @Autowired
    private AESConverter converter;

    @Test
    void 친구추가시_한쪽만_친구신청상태로_등록() throws Exception {
        // given
        LinkedList<Object> objects = new LinkedList<>();
        ArrayList<Object> list = new ArrayList<>();
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(friend);
        String serialize = converter.serialize(String.valueOf(saveUser.getId()));

        // when
        System.out.println("=====Logic Start=====");

        friendService.applyFriend(new FriendApplyCommand(saveFriend.getId(), serialize)); //암호화되어있어야함, 근데 지금은 아니어서 안됌

        System.out.println("=====Logic End=====");
        // then
        assertThat(friendJpaRepository.findAll().size()).isEqualTo(2);
    }


}