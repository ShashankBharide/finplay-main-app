package com.finplay.mainapp.controller;

import com.finplay.mainapp.entity.DTO.PortfolioDTO;
import com.finplay.mainapp.entity.Portfolio;
import com.finplay.mainapp.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finplay/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    // Get Portfolio Details
    @GetMapping("/{userId}")
    public ResponseEntity<PortfolioDTO> getPortfolio(@PathVariable Long userId) {
        PortfolioDTO portfolio = portfolioService.getPortfolio(userId);
        return ResponseEntity.ok(portfolio);
    }

    // Create or Update Portfolio
    @PostMapping
    public ResponseEntity<Portfolio> createOrUpdatePortfolio(@RequestBody Portfolio portfolio) {
        Portfolio updatedPortfolio = portfolioService.createOrUpdatePortfolio(portfolio);
        return ResponseEntity.ok(updatedPortfolio);
    }
}
