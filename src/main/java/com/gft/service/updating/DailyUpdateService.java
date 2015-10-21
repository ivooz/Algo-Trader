package com.gft.service.updating;

import com.gft.aspect.Log;
import com.gft.repository.HistoryDAO;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by iozi on 14/10/2015.
 */
@Service
public class DailyUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(DailyUpdateService.class);

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StatisticsUpdateService statisticsUpdateService;

    @Autowired
    @Qualifier("databaseHistoryDao")
    HistoryDAO databaseHistoryDao;

    @Autowired
    StockUpdateService stockUpdateService;

    @Log
    public void updateStocks() {
        final Date today = new Date();
        stockRepository.findAll().parallelStream().forEach(stock -> {
            try {
                stockUpdateService.updateStock(stock);
                statisticsUpdateService.updateStatistics(stock, today, databaseHistoryDao);
            } catch (DataAccessException e) {
                logger.error("Unable to update data for stock" + stock.getTicker());
            }
        });
    }
}
