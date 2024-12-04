package com.finplay.mainapp.entity.DTO;

import com.finplay.mainapp.entity.PortfolioStock;
import lombok.Data;

import java.util.List;

@Data
public class PortfolioDTO {
    private Long id;
    private Double totalInvestment;
    private Double currentValue;
    private Double totalPL;
    private List<PortfolioStock> stocks;
}
