package com.finplay.mainapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_investment", nullable = false)
    private Double totalInvestment;

    @Column(name = "current_value", nullable = false)
    private Double currentValue;

    @Column(name = "total_pnl", nullable = false)
    private Double totalPL;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private List<PortfolioStock> stocks;
}
