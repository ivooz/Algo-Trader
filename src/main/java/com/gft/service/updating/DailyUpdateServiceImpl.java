package com.gft.service.updating;

import com.gft.repository.DatabaseHistoryDao;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

/**
 * Created by iozi on 14/10/2015.
 */
@Service
@Scope(value =  WebApplicationContext.SCOPE_REQUEST)
public class DailyUpdateServiceImpl implements DailyUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(DailyUpdateServiceImpl.class);

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StatisticsUpdateService statisticsUpdateService;

    @Autowired
    @Qualifier("databaseHistoryDao")
    DatabaseHistoryDao databaseHistoryDao;

    @Autowired
    StockUpdateService stockUpdateService;

    @Override
    public void updateStocks() {
        logger.info("Starting stock updates");
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
