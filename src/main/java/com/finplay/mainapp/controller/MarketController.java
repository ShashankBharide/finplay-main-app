package com.finplay.mainapp.controller;

import com.finplay.mainapp.entity.Stock;
import com.finplay.mainapp.entity.Trade;
import com.finplay.mainapp.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finplay/api/market")
public class MarketController {

    @Autowired
    private MarketService marketService;

    // Stock List API
    @GetMapping("/stocks")
    public ResponseEntity<List<Stock>> getStockList() {
        List<Stock> stocks = marketService.getStockList();
        return ResponseEntity.ok(stocks);
    }

    // Stock Detail API
    @GetMapping("/stocks/{symbol}")
    public ResponseEntity<Stock> getStockDetail(@PathVariable String symbol) {
        Stock stock = marketService.getStockDetail(symbol);
        return ResponseEntity.ok(stock);
    }

    // Buy Stock API
    @PostMapping("/trade/buy")
    public ResponseEntity<Trade> buyStock(@RequestParam Long userId,
                                          @RequestParam String stockSymbol,
                                          @RequestParam int quantity,
                                          @RequestParam double price) {
        Trade trade = marketService.buyStock(userId, stockSymbol, quantity, price);
        return ResponseEntity.ok(trade);
    }
}
