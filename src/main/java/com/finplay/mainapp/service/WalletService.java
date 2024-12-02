package com.finplay.mainapp.service;

import com.finplay.mainapp.entity.Transaction;
import com.finplay.mainapp.entity.Wallet;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface WalletService {
    Double getCurrentBalance(Long userId);
    List<Transaction> getTransactionList(Long userId);
    void deductBalance(Long userId, Double amount);
    void addBalance(Long userId, Double amount);
    Wallet getWalletByUser(Long userId);
    List<Transaction> getTransactionsByUser(Long userId);
}