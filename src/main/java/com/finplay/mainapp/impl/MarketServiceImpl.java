package com.finplay.mainapp.impl;

import com.finplay.mainapp.entity.Stock;
import com.finplay.mainapp.entity.Trade;
import com.finplay.mainapp.repo.StockRepository;
import com.finplay.mainapp.repo.TradeRepository;
import com.finplay.mainapp.service.MarketService;
import com.finplay.mainapp.service.PortfolioService;
import com.finplay.mainapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private WalletService walletService;

    @Override
    public List<Stock> getStockList() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getStockDetail(String symbol) {
        return stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Stock not found with symbol: " + symbol));
    }

    @Override
    public Stock getStockDetailTop(String symbol) {
        return stockRepository.findTopByNameOrSymbolOrderByValueDesc(symbol);
    }

    @Override
    public Trade buyStock(Long userId, String stockSymbol, int quantity, double price) {
        // Fetch stock details
        Stock stock = getStockDetailTop(stockSymbol);

        // Calculate total amount
        double totalAmount = quantity * price;

        // Deduct amount from wallet
        walletService.deductBalance(userId, totalAmount);

        // Add stock to portfolio
        portfolioService.addStockToPortfolio(userId, stockSymbol, stock.getName(), quantity, price);

        // Create trade record
        Trade trade = new Trade(null, userId, stockSymbol, "BUY", quantity, price, totalAmount, LocalDateTime.now());
        return tradeRepository.save(trade);
    }
}