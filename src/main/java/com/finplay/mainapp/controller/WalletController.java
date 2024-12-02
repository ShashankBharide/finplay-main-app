package com.finplay.mainapp.controller;

import com.finplay.mainapp.entity.Transaction;
import com.finplay.mainapp.entity.Wallet;
import com.finplay.mainapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/finplay/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    // Get Current Balance
    @GetMapping("/balance")
    public ResponseEntity<Double> getCurrentBalance(@RequestParam Long userId) {
        Double balance = walletService.getCurrentBalance(userId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@RequestParam Long userId) {
        List<Transaction> transactions = walletService.getTransactionsByUser(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping
    public ResponseEntity<Wallet> getWallet(@RequestParam Long userId) {
        Wallet wallet = walletService.getWalletByUser(userId);
        return ResponseEntity.ok(wallet);
    }
}