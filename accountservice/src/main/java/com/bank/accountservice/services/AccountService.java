package com.bank.accountservice.services;

import java.math.BigDecimal;

import com.bank.accountservice.entities.AccountDTO;
import com.bank.accountservice.entities.CreateAccountRequest;

public interface AccountService {
	AccountDTO createAccount(CreateAccountRequest request);
	AccountDTO getAccount(Long accountId);
	AccountDTO updateBalance(Long accountId, BigDecimal newBalance);

	



}
