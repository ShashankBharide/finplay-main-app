package com.finplay.mainapp.controller;

import com.finplay.mainapp.entity.Stock;
import com.finplay.mainapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finplay/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    // Search API - Search by Name, Symbol, Price, or Profit
    @GetMapping("/search")
    public ResponseEntity<List<Stock>> searchStocks(@RequestParam String keyword) {
        List<Stock> stocks = stockService.searchStocks(keyword);
        return ResponseEntity.ok(stocks);
    }

    // Stock List API
    @GetMapping
    public ResponseEntity<List<Stock>> getStockList() {
        List<Stock> stocks = stockService.getStockList();
        return ResponseEntity.ok(stocks);
    }

    // Stock Detail API
    @GetMapping("/{symbol}")
    public ResponseEntity<Stock> getStockDetail(@PathVariable String symbol) {
        Stock stock = stockService.getStockDetail(symbol);
        return ResponseEntity.ok(stock);
    }
    // Search API - Search by Name, Symbol, Price, or Profit
    @GetMapping("/search-latest")
    public ResponseEntity<List<Stock>> searchStocksLatest(@RequestParam String keyword) {
        List<Stock> stocks = stockService.getStockDetailLatest(keyword);
        return ResponseEntity.ok(stocks);
    }
}