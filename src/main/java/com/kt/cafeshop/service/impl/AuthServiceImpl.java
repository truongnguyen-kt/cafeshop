package com.kt.cafeshop.service.impl;

import org.springframework.stereotype.Service;

import com.kt.cafeshop.service.AccountService;
import com.kt.cafeshop.service.AuthService;
import com.kt.cafeshop.utils.AccountDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountService accountService;

    @Override
    public AccountDTO checkLogin(String username, String password) {
        return accountService.checkLogin(username, password);
    }

}
