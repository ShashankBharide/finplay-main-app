package com.finplay.mainapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "portfolio_stocks")
public class PortfolioStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stockSymbol;

    @Column(nullable = false)
    private String stockName;

    @Column(nullable = false)
    private Double quantity;

    @Column(name = "avg_cost", nullable = false)
    private Double avgCost;

    @Column(name = "current_value", nullable = false)
    private Double currentValue;

    @Column(name = "pnl", nullable = false)
    private Double pnl;

    @Column(name = "net_change")
    private Double netChange;

    @Column(name = "day_change")
    private Double dayChange;


}
