package com.kt.cafeshop.controller;

import com.kt.cafeshop.service.AccountService;
import com.kt.cafeshop.utils.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cafeshop/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/getAll")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getAccountByUsername(@PathVariable String username) {
        AccountDTO account = accountService.getAccountByUsername(username);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account not found with username: " + username);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO accountDTO) {
        try {
            AccountDTO createdAccount = accountService.createAccount(accountDTO);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating account: " + e.getMessage());
        }
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateAccount(@PathVariable String username, @RequestBody AccountDTO accountDTO) {
        try {
            AccountDTO updatedAccount = accountService.updateAccount(username, accountDTO);
            if (updatedAccount != null) {
                return ResponseEntity.ok(updatedAccount);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Account not found with username: " + username);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating account: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteAccount(@PathVariable String username) {
        boolean deleted = accountService.deleteAccount(username);
        if (deleted) {
            return ResponseEntity.ok("Account deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account not found or could not be deleted: " + username);
        }
    }
}
