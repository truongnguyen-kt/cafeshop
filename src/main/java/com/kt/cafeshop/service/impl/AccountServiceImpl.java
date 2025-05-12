package com.kt.cafeshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kt.cafeshop.entities.Account;
import com.kt.cafeshop.repository.AccountRepository;
import com.kt.cafeshop.service.AccountService;
import com.kt.cafeshop.utils.AccountDTO;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::mapToAccountDTO)
                .toList();
    }

    @Override
    public AccountDTO getAccountByUsername(String username) {
        Optional<Account> optionalAccount = accountRepository.findById(username);
        return optionalAccount.map(this::mapToAccountDTO).orElse(null);
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        account.setRole(accountDTO.getRole());

        Account savedAccount = accountRepository.save(account);
        return mapToAccountDTO(savedAccount);
    }

    @Override
    public AccountDTO updateAccount(String username, AccountDTO accountDTO) {
        Optional<Account> optionalAccount = accountRepository.findById(username);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
            account.setRole(accountDTO.getRole());

            Account updatedAccount = accountRepository.save(account);
            return mapToAccountDTO(updatedAccount);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteAccount(String username) {
        if (accountRepository.existsById(username)) {
            accountRepository.deleteById(username);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AccountDTO checkLogin(String username, String password) {
        return mapToAccountDTO(accountRepository.findByUsernameAndPassword(username, password));
    }

    private final AccountDTO mapToAccountDTO(Account account) {
        if (account == null) {
            return null;
        }
        AccountDTO dto = new AccountDTO();
        dto.setUsername(account.getUsername());
        dto.setRole(account.getRole());
        dto.setPassword(account.getPassword());
        return dto;
    }
}
