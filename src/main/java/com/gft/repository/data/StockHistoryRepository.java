package com.gft.repository.data;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by iozi on 13/10/2015.
 */
public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
}
