package com.gft.repository.data;

import com.gft.model.db.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by iozi on 13/10/2015.
 */
public interface StockRepository extends JpaRepository<Stock, Long> {
}
