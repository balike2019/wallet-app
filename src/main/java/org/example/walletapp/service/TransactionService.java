package org.example.walletapp.service;

import org.example.walletapp.domain.Transaction;
import org.example.walletapp.domain.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    Transaction recordTransaction(Long accountId, BigDecimal amount, TransactionType type);
    List<Transaction> getTransactionHistory(Long accountId);
}
