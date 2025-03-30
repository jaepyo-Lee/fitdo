package com.jaejoo.fitdomysql.domain.user.core;

import lombok.Getter;

import java.util.Objects;

@Getter
public class User {
    private Account account;
    private int height;
    private int weight;
    private String nickname;
    private String name;


    public Long getUserId() {
        return account.getUserId();
    }

    public User changeActive(Boolean activeStatus) {
        this.account = new Account(account.getAuthId(), account.getNewFlag(), account.getUserId(), account.getRole(), account.getAuthType(), activeStatus);
        return this;
    }

    public User(Account account) {
        this.account = account;
    }

    public User(Account account, int height, int weight, String nickname) {
        this.account = account;
        this.height = height;
        this.weight = weight;
        this.nickname = nickname;
    }

    public User register(Integer height, Integer weight, String nickname) {
        return new User(account.complete(), height, weight, nickname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return height == user.height && weight == user.weight && account.equals(user.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, height, weight);
    }
}
