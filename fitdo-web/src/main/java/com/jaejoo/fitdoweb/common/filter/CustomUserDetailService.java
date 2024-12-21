package com.jaejoo.fitdoweb.common.filter;

import com.jaejoo.fitdocore.user.UserService;
import com.jaejoo.fitdoweb.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * Todo
 * Work) JWT에 있는 userId꺼내오는것으로 수정하기
 * Write-Date)
 * Finish-Date)
 */
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
        boolean isExist = userService.isExist(username);
        if (!isExist) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetail(Long.valueOf(username));
    }
}