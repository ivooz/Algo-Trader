package com.gft.service.creating;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.gft.aspect.Log;
import com.gft.model.db.Stock;
import com.gft.repository.ForwardableHistoryDAO;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.gft.service.downloading.DataDownloadService;
import com.gft.service.updating.StatisticsUpdateService;

/**
 * Created by iozi on 15/10/2015.
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class NewStockServiceImpl implements NewStockService {

    private static final Logger logger = LoggerFactory.getLogger(NewStockServiceImpl.class);

    @Autowired
    DataDownloadService dataDownloadService;

    @Autowired
    ForwardableHistoryDAO memoryHistoryDao;

    @Autowired
    StatisticsUpdateService updateService;

    @Autowired
    StockRepository stockRepository;

    @Log
    @Override
    public void addNewStock(String ticker) throws DataAccessException, InsufficientDataException {
        Stock stock = dataDownloadService.downloadNewStock(ticker);
        stockRepository.save(stock);
        Date historyHead;
        for (int i = 0; i < memoryHistoryDao.getHistorySize(stock); i++) {
            historyHead = memoryHistoryDao.getCurrentDay(stock).getDate();
            updateService.updateStatistics(stock, historyHead, memoryHistoryDao);
            memoryHistoryDao.forwardHistory();
        }
        stockRepository.save(stock);
    }
}
