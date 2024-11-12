package com.jaejoo.fitdo.global.security.filter;

import com.jaejoo.fitdo.domain.auth.service.domain.CustomUserDetail;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.infra.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
        Account account = accountRepository.findByUserId(Long.parseLong(username));
        return new CustomUserDetail(account.getUserId());
    }
}