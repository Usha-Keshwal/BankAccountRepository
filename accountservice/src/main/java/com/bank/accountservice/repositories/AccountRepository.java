package com.bank.accountservice.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.accountservice.entities.Account;


public interface AccountRepository extends JpaRepository<Account, Long> {
}