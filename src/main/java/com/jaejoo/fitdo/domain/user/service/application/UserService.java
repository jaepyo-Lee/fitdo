package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.infra.repository.UserRepository;
import com.jaejoo.fitdo.domain.user.service.application.req.CompleteSignUpCommand;
import com.jaejoo.fitdo.domain.user.service.application.req.NickNameIsDuplicateCommand;
import com.jaejoo.fitdo.domain.user.service.application.res.IsNickNameDuplicateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User completeSignUp(CompleteSignUpCommand command) {
        User user = userRepository.findById(command.getUserId());
        user = user.register(command.getHeight(), command.getWeight(), command.getNickname());
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    public IsNickNameDuplicateResult isDuplicate(NickNameIsDuplicateCommand command) {
        return new IsNickNameDuplicateResult(userRepository.isExistNickName(command.nickName()));
    }
}
