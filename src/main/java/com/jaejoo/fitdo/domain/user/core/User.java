package com.jaejoo.fitdo.domain.user.core;

import lombok.Getter;

import java.util.Objects;

@Getter
public class User {
    private final Account account;
    private int height;
    private int weight;
    private String  name;

    public User(Account account) {
        this.account = account;
    }

    public User(Account account, int height, int weight) {
        this.account = account;
        this.height = height;
        this.weight = weight;
    }

    public User register(Integer height, Integer weight) {
        return new User(account.complete(), height, weight);
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
