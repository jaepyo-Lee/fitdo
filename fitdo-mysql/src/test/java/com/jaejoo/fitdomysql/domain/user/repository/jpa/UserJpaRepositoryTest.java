package com.jaejoo.fitdomysql.domain.user.repository.jpa;

import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserJpaRepositoryTest {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Test
    void saveAll() {
        // given
        long total=0;
        List<UserJpaEntity> users=new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            users.add(new UserJpaEntity("" + i, AuthType.KAKAO, "user" + i, true, GrantRole.ROLE_USER));
        }
        userJpaRepository.saveAll(users);
        long end = System.currentTimeMillis();
        total+=end-start;
        System.out.println((double)(total)/1000+" 초 걸림"    );

        // when
        System.out.println("=====Logic Start=====");


        System.out.println("=====Logic End=====");
        // then
    }
    @Test
    void save() {
        // given
        long total=0;
            long start = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                userJpaRepository.save(new UserJpaEntity("" + i, AuthType.KAKAO, "user" + i, true, GrantRole.ROLE_USER));
            }
            long end = System.currentTimeMillis();
            total+=end-start;
        System.out.println((double)(total)/1000+" 초 걸림"    );
        // when
        System.out.println("=====Logic Start=====");


        System.out.println("=====Logic End=====");
        // then
    }



}