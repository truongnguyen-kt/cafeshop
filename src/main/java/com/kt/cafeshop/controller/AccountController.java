package com.kt.cafeshop.controller;

import com.kt.cafeshop.service.AccountService;
import com.kt.cafeshop.utils.AccountDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cafeshop/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/getAll")
    public List<AccountDTO> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{username}")
    public AccountDTO getAccountByUsername(@PathVariable String username) {
        return accountService.getAccountByUsername(username);
    }

    @PostMapping("/create")
    public AccountDTO createAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO);
    }

    @PutMapping("/update/{username}")
    public AccountDTO updateAccount(@PathVariable String username, @RequestBody AccountDTO accountDTO) {
        return accountService.updateAccount(username, accountDTO);
    }

    @DeleteMapping("/delete/{username}")
    public boolean deleteAccount(@PathVariable String username) {
        return accountService.deleteAccount(username);
    }
}
