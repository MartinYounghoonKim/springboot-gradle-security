package com.inflearn.springsecurity.service;

import com.inflearn.springsecurity.entity.Account;
import com.inflearn.springsecurity.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    // UserDetailsService 가 인증을 처리하는 클래스는 아니다.
    // 단순한 DAO 역할이라고 이해하면 된다. In Memory 든 혹은 DB 든 유저 정보를 가져와서 Spring security 에게 제공하는 역할을 한다.
    // 실제 인증은 AuthenticationManager 에서 처리 한다.
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public Account signUp (Account account) {
        account.encodePassword(passwordEncoder);
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }
}
