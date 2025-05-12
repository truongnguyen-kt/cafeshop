package com.kt.cafeshop.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kt.cafeshop.entities.Account;
import com.kt.cafeshop.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account acc = accountRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản"));

        return User.builder()
                .username(acc.getUsername())
                .password(acc.getPassword())
                .roles(acc.getRole()) // Spring tự thêm "ROLE_" prefix
                .build();
    }
}
