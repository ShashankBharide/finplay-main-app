package com.finplay.mainapp.impl;

import com.finplay.mainapp.entity.Transaction;
import com.finplay.mainapp.entity.Wallet;
import com.finplay.mainapp.repo.TransactionRepository;
import com.finplay.mainapp.repo.WalletRepository;
import com.finplay.mainapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Double getCurrentBalance(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for userId: " + userId));
        return wallet.getBalance();
    }

    @Override
    public List<Transaction> getTransactionList(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for userId: " + userId));
        return transactionRepository.findByWalletId(wallet.getId());
    }

    @Override
    public void deductBalance(Long userId, Double amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for userId: " + userId));
        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);

        Transaction transaction = new Transaction(null, wallet, "DEBIT", amount, LocalDateTime.now(), "SUCCESS");
        transactionRepository.save(transaction);
    }

    @Override
    public void addBalance(Long userId, Double amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for userId: " + userId));
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);

        Transaction transaction = new Transaction(null, wallet, "CREDIT", amount, LocalDateTime.now(), "SUCCESS");
        transactionRepository.save(transaction);
    }

    @Override
    public Wallet getWalletByUser(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for userId: " + userId));
    }
    @Override
    public List<Transaction> getTransactionsByUser(Long userId) {
        Wallet wallet = getWalletByUser(userId);
        return transactionRepository.findByWalletId(wallet.getId());
    }
}