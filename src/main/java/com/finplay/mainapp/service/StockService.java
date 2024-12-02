package com.finplay.mainapp.service;

import com.finplay.mainapp.entity.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockService {

    List<Stock> searchStocks(String keyword);
    List<Stock> getStockList();
    Stock getStockDetail(String symbol);

}
