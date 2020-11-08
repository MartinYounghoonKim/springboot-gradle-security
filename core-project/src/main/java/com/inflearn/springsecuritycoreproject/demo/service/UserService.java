package com.inflearn.springsecuritycoreproject.demo.service;

import com.inflearn.springsecuritycoreproject.demo.dto.AccountDto;
import com.inflearn.springsecuritycoreproject.demo.entity.Account;
import com.inflearn.springsecuritycoreproject.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser (AccountDto accountDto) {
        Account account = Account.builder()
                .username(accountDto.getUsername())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .email(accountDto.getEmail())
                .age(accountDto.getAge())
                .role(accountDto.getRole())
                .build();
        userRepository.save(account);
    }
}
