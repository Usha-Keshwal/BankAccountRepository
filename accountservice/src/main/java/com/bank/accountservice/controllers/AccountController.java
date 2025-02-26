package com.bank.accountservice.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.accountservice.entities.AccountDTO;
import com.bank.accountservice.entities.CreateAccountRequest;
import com.bank.accountservice.entities.UpdateBalanceRequest;
import com.bank.accountservice.services.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody CreateAccountRequest request) {
		return ResponseEntity.ok(accountService.createAccount(request));
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
		return ResponseEntity.ok(accountService.getAccount(id));
	}

	@PutMapping("/{id}/balance")
	public ResponseEntity<AccountDTO> updateBalance(@PathVariable Long id,
			@Valid @RequestBody UpdateBalanceRequest request) {
		return ResponseEntity.ok(accountService.updateBalance(id, request.getNewBalance()));
	}

	

}
