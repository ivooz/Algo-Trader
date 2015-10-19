package com.gft.repository.data;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by iozi on 13/10/2015.
 */
@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {

    List<StockHistory> findByStockOrderByDateDesc(Stock stock);

}
