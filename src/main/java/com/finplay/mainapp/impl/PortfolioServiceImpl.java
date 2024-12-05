package com.finplay.mainapp.impl;


import com.finplay.mainapp.entity.DTO.PortfolioDTO;
import com.finplay.mainapp.entity.Portfolio;
import com.finplay.mainapp.entity.PortfolioStock;
import com.finplay.mainapp.entity.User;
import com.finplay.mainapp.repo.PortfolioRepository;
import com.finplay.mainapp.repo.UserRepository;
import com.finplay.mainapp.service.PortfolioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PortfolioDTO getPortfolio(Long userId) {
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found for userId: " + userId));

        PortfolioDTO dto = new PortfolioDTO();
        BeanUtils.copyProperties(portfolio, dto);
        return dto;
    }

    @Override
    public Portfolio createOrUpdatePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    @Override
    public void addStockToPortfolio(Long userId, String stockSymbol, String stockName, int quantity, double price) {
        // Fetch user's portfolio or create a new one if not found
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));
                    return new Portfolio(null, user, 0.0, 0.0, 0.0, new ArrayList<>());
                });

        // Check if stock already exists in the portfolio
        Optional<PortfolioStock> existingStockOpt = portfolio.getStocks().stream()
                .filter(stock -> stock != null &&
                        stock.getStockSymbol() != null &&
                        stock.getStockSymbol().equals(stockSymbol))
                .findFirst();

        if (existingStockOpt.isPresent()) {
            // Update existing stock entry
            PortfolioStock existingStock = existingStockOpt.get();

            // Null-safe quantity handling
            double existingQuantity = existingStock.getQuantity() != null ? existingStock.getQuantity() : 0.0;
            double existingAvgCost = existingStock.getAvgCost() != null ? existingStock.getAvgCost() : 0.0;

            double totalQuantity = existingQuantity + quantity;
            double avgCost = ((existingQuantity * existingAvgCost) + (quantity * price)) / totalQuantity;

            existingStock.setQuantity(totalQuantity);
            existingStock.setAvgCost(avgCost);
            existingStock.setCurrentValue(totalQuantity * price);
            existingStock.setPnl(existingStock.getCurrentValue() - (totalQuantity * avgCost));
        } else {
            // Add new stock to the portfolio
            PortfolioStock newStock = new PortfolioStock();
            newStock.setStockSymbol(stockSymbol);
            newStock.setStockName(stockName);
            newStock.setQuantity((double) quantity);
            newStock.setAvgCost(price);
            newStock.setCurrentValue(quantity * price);
            newStock.setPnl(0.0);
            portfolio.getStocks().add(newStock);
        }

        // Update portfolio metadata with null-safe streaming
        double totalInvestment = portfolio.getStocks().stream()
                .filter(stock -> stock != null &&
                        stock.getQuantity() != null &&
                        stock.getAvgCost() != null)
                .mapToDouble(stock -> stock.getQuantity() * stock.getAvgCost())
                .sum();

        double currentValue = portfolio.getStocks().stream()
                .filter(stock -> stock != null && stock.getCurrentValue() != null)
                .mapToDouble(PortfolioStock::getCurrentValue)
                .sum();

        double totalPL = currentValue - totalInvestment;

        portfolio.setTotalInvestment(totalInvestment);
        portfolio.setCurrentValue(currentValue);
        portfolio.setTotalPL(totalPL);

        // Save portfolio
        portfolioRepository.save(portfolio);
    }
}

