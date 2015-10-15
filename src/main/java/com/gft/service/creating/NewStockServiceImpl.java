package com.gft.service.creating;

import com.gft.model.db.Stock;
import com.gft.repository.MemoryHistoryDao;
import com.gft.repository.data.InsufficientDataException;
import com.gft.service.DataAccessException;
import com.gft.service.downloading.DataDownloadService;
import com.gft.service.updating.StatisticsUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

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
    MemoryHistoryDao memoryHistoryDao;

    @Autowired
    StatisticsUpdateService updateService;

    @Override
    public void addNewStock(String ticker) throws DataAccessException, InsufficientDataException {
        logger.info("Adding stock with ticker " + ticker);
        Stock stock = dataDownloadService.downloadNewStock(ticker);
        for (int i = 0; i < memoryHistoryDao.getHistorySize(stock); i++) {
            Date historyHead = memoryHistoryDao.getCurrentDate(stock);
            updateService.updateStatistics(stock, historyHead, memoryHistoryDao);
        }
    }
}