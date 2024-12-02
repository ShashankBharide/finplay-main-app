package com.finplay.mainapp.impl;

import com.finplay.mainapp.entity.Stock;
import com.finplay.mainapp.repo.StockRepository;
import com.finplay.mainapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> searchStocks(String keyword) {
        return stockRepository.findByNameContainingIgnoreCaseOrSymbolContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public List<Stock> getStockList() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getStockDetail(String symbol) {
        return stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Stock not found with symbol: " + symbol));
    }
}
