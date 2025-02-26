package com.bank.accountservice.services.impl;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.accountservice.entities.Account;
import com.bank.accountservice.entities.AccountDTO;
import com.bank.accountservice.entities.CreateAccountRequest;
import com.bank.accountservice.exceptions.AccountNotFoundException;
import com.bank.accountservice.repositories.AccountRepository;
import com.bank.accountservice.services.AccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
	@Autowired
	private AccountRepository accountRepository;

    public AccountDTO createAccount(CreateAccountRequest request) {
        Account account = new Account();
        account.setCustomerName(request.getCustomerName());
        return mapToDTO(accountRepository.save(account));
    }

    public AccountDTO getAccount(Long accountId) {
        return mapToDTO(getAccountEntity(accountId));
    }

    public Account getAccountEntity(Long accountId) {
        return accountRepository.findById(accountId)
        		//.orElseThrow(() -> new IllegalArgumentException("Account not found"));
		.orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + accountId));
    }

    private AccountDTO mapToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setCustomerName(account.getCustomerName());
        dto.setBalance(account.getBalance());
        return dto;
    }
    @Transactional
    public AccountDTO updateBalance(Long accountId, BigDecimal newBalance) {
    	if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        Account account = getAccountEntity(accountId);
        account.setBalance(newBalance);
        return mapToDTO(accountRepository.save(account));
    }

}
