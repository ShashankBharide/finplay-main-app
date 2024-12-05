package com.finplay.mainapp.repo;

import com.finplay.mainapp.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByNameContainingIgnoreCaseOrSymbolContainingIgnoreCase(String name, String symbol);
    Optional<Stock> findBySymbol(String symbol);

    @Query("SELECT s FROM Stock s WHERE " +
            "(LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.symbol) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND s.date = (SELECT MAX(sub.date) FROM Stock sub WHERE sub.symbol = s.symbol)")
    List<Stock> findLatestByNameOrSymbol(@Param("keyword") String keyword);

    @Query("SELECT s FROM Stock s WHERE s.symbol = :symbol ORDER BY s.date DESC LIMIT 1")
    Optional<Stock> findLatestStockBySymbol(@Param("symbol") String symbol);

}
