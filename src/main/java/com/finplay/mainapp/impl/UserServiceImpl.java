package com.finplay.mainapp.impl;

import com.finplay.mainapp.entity.Portfolio;
import com.finplay.mainapp.entity.User;
import com.finplay.mainapp.entity.Wallet;
import com.finplay.mainapp.repo.PortfolioRepository;
import com.finplay.mainapp.repo.UserRepository;
import com.finplay.mainapp.repo.WalletRepository;
import com.finplay.mainapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public User createUser(User user) {
        // Save user
        User savedUser = userRepository.save(user);

        // Create default portfolio for the user
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(savedUser);
        portfolio.setTotalInvestment(0.0);
        portfolio.setCurrentValue(0.0);
        portfolio.setTotalPL(0.0);
        portfolioRepository.save(portfolio);

        // Create default wallet for the user
        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        wallet.setBalance(0.0);
        walletRepository.save(wallet);

        return savedUser;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
}
