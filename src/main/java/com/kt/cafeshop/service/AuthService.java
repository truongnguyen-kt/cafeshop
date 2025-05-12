package com.kt.cafeshop.service;

import com.kt.cafeshop.utils.AccountDTO;

public interface AuthService {
    public AccountDTO checkLogin(String username, String password);
}
