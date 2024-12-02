package com.jaejoo.fitdo.domain.user.infra.repository;

import com.jaejoo.fitdo.domain.user.core.User;

public interface UserRepository {
    User findById(Long id);

    User save(User user);

    boolean isExistNickName(String s);
}
