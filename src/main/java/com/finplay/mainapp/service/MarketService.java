package com.finplay.mainapp.service;

import com.finplay.mainapp.entity.Stock;
import com.finplay.mainapp.entity.Trade;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface MarketService {
    List<Stock> getStockList();
    Stock getStockDetail(String symbol);
    Trade buyStock(Long userId, String stockSymbol, int quantity, double price);
}
