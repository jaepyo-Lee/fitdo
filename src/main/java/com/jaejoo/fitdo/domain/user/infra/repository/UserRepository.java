package com.jaejoo.fitdo.domain.user.infra.repository;

import com.jaejoo.fitdo.domain.user.core.User;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserRepository {
    User findById(Long id);

    User save(User user);

    boolean isExistNickName(String s);
    Integer findAllSize();
}
