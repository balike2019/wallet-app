package org.example.walletapp.service;

import org.example.walletapp.domain.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account createAccount(Long userId);  // Changed to accept user ID
    BigDecimal checkBalance(Long accountId);
    Account deposit(Long accountId, BigDecimal amount);
    Account withdraw(Long accountId, BigDecimal amount) throws Exception; // throws Exception for insufficient funds
}
