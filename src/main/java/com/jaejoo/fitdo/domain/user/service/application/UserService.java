package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.exercise.infra.repository.CategoryCommandRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.service.domain.CategoryInitializer;
import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.infra.repository.UserRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import com.jaejoo.fitdo.domain.user.service.application.req.CompleteSignUpCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CategoryInitializer categoryInitializer;
    private final CategoryCommandRepository categoryCommandRepository;

    public User completeSignUp(CompleteSignUpCommand command) {
        User user = userRepository.findById(command.getUserId());
        user = user.register(command.getHeight(), command.getWeight(), command.getNickname());
        User saveUser = userRepository.save(user);
        List<CategoryJpaEntity> categories = categoryInitializer.init(UserJpaEntity.from(saveUser));
        categoryCommandRepository.saveAll(categories);
        return saveUser;
    }
}
