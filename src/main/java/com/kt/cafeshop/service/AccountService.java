package com.kt.cafeshop.service;

import java.util.List;

import com.kt.cafeshop.utils.AccountDTO;

public interface AccountService {
	public List<AccountDTO> getAllAccounts();

	public AccountDTO getAccountByUsername(String username);

	public AccountDTO createAccount(AccountDTO accountDTO);

	public AccountDTO updateAccount(String username, AccountDTO accountDTO);

	public boolean deleteAccount(String username);

	public AccountDTO checkLogin(String username, String password);
}
