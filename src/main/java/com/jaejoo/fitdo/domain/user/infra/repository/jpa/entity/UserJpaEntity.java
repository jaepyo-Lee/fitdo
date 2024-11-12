package com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.core.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String authId;
    private boolean newFlag;
    private int height;
    private int weight;
    private AuthType authType;

    public UserJpaEntity(String authId, AuthType platformType, String username, Boolean newFlag) {
        this.authId = authId;
        this.username = username;
        this.newFlag = newFlag;
        this.authType = platformType;
    }

    public static UserJpaEntity from(String authId, AuthType platformType, String username, Boolean newFlag) {
        return new UserJpaEntity(authId, platformType, username, newFlag);
    }

    public Account toAccountModel() {
        return new Account(authId, newFlag, id);
    }

    public User toUserModel() {
        return new User(new Account(authId, newFlag, id), height, weight);
    }
}
