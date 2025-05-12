package com.kt.cafeshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kt.cafeshop.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    public Account findByUsernameAndPassword(String username, String password);

    Optional<Account> findByUsername(String username);
}
