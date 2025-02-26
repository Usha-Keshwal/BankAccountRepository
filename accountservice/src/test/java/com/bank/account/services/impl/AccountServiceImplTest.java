package com.bank.account.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.accountservice.entities.Account;
import com.bank.accountservice.entities.AccountDTO;
import com.bank.accountservice.entities.CreateAccountRequest;
import com.bank.accountservice.exceptions.AccountNotFoundException;
import com.bank.accountservice.repositories.AccountRepository;
import com.bank.accountservice.services.impl.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account testAccount;
    private CreateAccountRequest createRequest;

    @BeforeEach
    void setUp() {
        // Setup test account
        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setCustomerName("Test Customer");
        testAccount.setBalance(BigDecimal.valueOf(100.00));
        
        // Setup create account request
        createRequest = new CreateAccountRequest();
        createRequest.setCustomerName("Test Customer");
    }

    @Test
    void createAccount_ShouldReturnNewAccount() {
        // Arrange
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            savedAccount.setId(1L);
            savedAccount.setBalance(BigDecimal.ZERO);
            return savedAccount;
        });

        // Act
        AccountDTO result = accountService.createAccount(createRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Customer", result.getCustomerName());
        assertEquals(BigDecimal.ZERO, result.getBalance());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void getAccount_ExistingId_ShouldReturnAccount() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // Act
        AccountDTO result = accountService.getAccount(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Customer", result.getCustomerName());
        assertEquals(BigDecimal.valueOf(100.00), result.getBalance());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void getAccount_NonExistingId_ShouldThrowException() {
        // Arrange
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(99L));
        verify(accountRepository, times(1)).findById(99L);
    }

    @Test
    void updateBalance_ExistingId_ShouldUpdateBalance() {
        // Arrange
        BigDecimal newBalance = BigDecimal.valueOf(200.00);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        // Act
        AccountDTO result = accountService.updateBalance(1L, newBalance);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Customer", result.getCustomerName());
        assertEquals(BigDecimal.valueOf(200.00), result.getBalance());
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(testAccount);
    }

    @Test
    void updateBalance_NegativeAmount_ShouldThrowException() {
        // Arrange
        BigDecimal negativeBalance = BigDecimal.valueOf(-50.00);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            accountService.updateBalance(1L, negativeBalance));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void updateBalance_NonExistingId_ShouldThrowException() {
        // Arrange
        BigDecimal newBalance = BigDecimal.valueOf(200.00);
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> 
            accountService.updateBalance(99L, newBalance));
        verify(accountRepository, times(1)).findById(99L);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void getAccountEntity_ExistingId_ShouldReturnAccount() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // Act
        Account result = accountService.getAccountEntity(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Customer", result.getCustomerName());
        assertEquals(BigDecimal.valueOf(100.00), result.getBalance());
        verify(accountRepository, times(1)).findById(1L);
    }

}
