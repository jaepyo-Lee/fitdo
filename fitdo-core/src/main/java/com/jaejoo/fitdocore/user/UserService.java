package com.jaejoo.fitdocore.user;

import com.jaejoo.fitdocore.user.req.CompleteSignUpCommand;
import com.jaejoo.fitdocore.user.req.NickNameIsDuplicateCommand;
import com.jaejoo.fitdocore.user.res.IsNickNameDuplicateResult;
import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public boolean completeSignUp(CompleteSignUpCommand command) {
        User user = userRepository.findById(command.getUserId());
        user = user.register(command.getHeight(), command.getWeight(), command.getNickname());
        User saveUser = userRepository.save(user);
        return true;
    }

    public IsNickNameDuplicateResult isDuplicate(NickNameIsDuplicateCommand command) {
        return new IsNickNameDuplicateResult(userRepository.isExistNickName(command.nickName()));
    }

    public boolean isExist(String userId) {
        User user = userRepository.findById(Long.valueOf(userId));
        return true;
    }
}
