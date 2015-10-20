package com.gft.repository;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.service.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used in DailyUpdateService
 */
@Repository("databaseHistoryDao")
public class DatabaseHistoryDao implements HistoryDAO {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHistoryDao.class);

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    private Map<String, List<StockHistory>> stockHistoryList = new HashMap<>();

    @Override
    public List<StockHistory> obtainStockHistoryForPeriod(Stock stock, int days) throws InsufficientDataException {
        historyNullGuard(stock);
        if (stockHistoryList.get(stock.getTicker()).size() < days) {
            logger.error(INSUFFICIENT_INTERVAL, "requested " + days + " actual " + stockHistoryList.size());
            throw new InsufficientDataException(INSUFFICIENT_INTERVAL);
        }
        //TODO not all records are necessary, top x is enough
        return stockHistoryList.get(stock.getTicker()).subList(0, days);
    }

    @Override
    public StockHistory getCurrentDay(Stock stock) throws InsufficientDataException, DataAccessException {
        historyNullGuard(stock);
        return stockHistoryList.get(stock.getTicker()).get(0);
    }

    @Override
    public int getHistorySize(Stock stock) throws InsufficientDataException, DataAccessException {
        historyNullGuard(stock);
        return stockHistoryList.get(stock.getTicker()).size();
    }

    private void historyNullGuard(Stock stock) {
        if (!stockHistoryList.containsKey(stock.getTicker())) {
            stockHistoryList.put(stock.getTicker(), stockHistoryRepository.findByStockOrderByDateDesc(stock));
        }
    }
}
