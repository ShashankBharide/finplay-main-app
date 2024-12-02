package com.finplay.mainapp.entity;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "stock_symbol", nullable = false)
    private String stockSymbol;

    @Column(name = "trade_type", nullable = false)
    private String tradeType; // BUY or SELL

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "trade_date", nullable = false)
    private LocalDateTime tradeDate;
}