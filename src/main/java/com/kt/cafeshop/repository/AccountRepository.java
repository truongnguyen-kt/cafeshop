package com.kt.cafeshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kt.cafeshop.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{

}
