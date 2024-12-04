package com.finplay.mainapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long stockId;

    @Column(nullable = false)
    private String symbol;

    private String name;

    private LocalDateTime date;

    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Long volume;

    @Column(name = "day_change")
    private Float dayChange;

    @Column(name = "day_change_percent")
    private Float dayChangePercent;

    private Float dividends;

    @Column(name = "stock_splits")
    private Float stockSplits;

    private String sector;

    @Column(columnDefinition = "TEXT")
    private String description;
}