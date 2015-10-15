package com.gft.service;

import com.gft.model.db.StockHistory;
import com.gft.repository.DatabaseHistoryDao;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;
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

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StatisticsUpdateService statisticsUpdateService;

    @Autowired
    @Qualifier("databaseHistoryDao")
    DatabaseHistoryDao databaseHistoryDao;

    @Override
    public void updateStocks() {
        stockRepository.findAll().parallelStream().forEach(stock -> {
            statisticsUpdateService.updateStatistics(stock, new Date(), databaseHistoryDao);
        });
    }
}
