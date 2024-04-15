package org.example.walletapp.service.impl;

import org.example.walletapp.domain.Account;
import org.example.walletapp.domain.Transaction;
import org.example.walletapp.domain.User;
import org.example.walletapp.repository.AccountRepository;
import org.example.walletapp.repository.UserRepository;
import org.example.walletapp.service.AccountService;
import org.example.walletapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.walletapp.domain.TransactionType;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public Account createAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Account account = new Account();
        account.setUser(user);
        return accountRepository.save(account);
    }


    @Override
    public BigDecimal checkBalance(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Invalid account ID"));
        return account.getBalance();
    }

    @Override
    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Invalid account ID"));
        account.setBalance(account.getBalance().add(amount));
        Account savedAccount = accountRepository.save(account);
        transactionService.recordTransaction(savedAccount.getId(), amount, TransactionType.DEPOSIT);
        return savedAccount;
    }

    @Override
    public Account withdraw(Long accountId, BigDecimal amount) throws Exception {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Invalid account ID"));
        BigDecimal newBalance = account.getBalance().subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Insufficient funds");
        }
        account.setBalance(newBalance);
        Account savedAccount = accountRepository.save(account);
        transactionService.recordTransaction(savedAccount.getId(), amount, TransactionType.DEPOSIT);

        return savedAccount;
    }
}
