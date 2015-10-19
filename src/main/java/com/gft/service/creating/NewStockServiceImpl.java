package com.gft.service.creating;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import com.gft.service.updating.AlgorithmHistoryUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    AlgorithmHistoryUpdateService historyUpdateService;



    @Log
    @Override
    public void addNewStock(String ticker) throws DataAccessException, InsufficientDataException {
        System.out.println(memoryHistoryDao);
        Stock stock = dataDownloadService.downloadNewStock(ticker);
        stockRepository.save(stock);
        Date historyHead;
        Calendar cal = Calendar.getInstance();
        int nextUpdate = 6;
        for (int i = 0; i < memoryHistoryDao.getHistorySize(stock); i++) {
            historyHead = memoryHistoryDao.getCurrentDay(stock).getDate();
            cal.setTime(historyHead);
            if(cal.get(Calendar.MONTH) == nextUpdate ) {
                //TODO pass stock/ticker
//                historyUpdateService.saveAlgorithmStatisticsWithDate(historyHead);
                nextUpdate = (nextUpdate+6)%12;
            }
            updateService.updateStatistics(stock, historyHead, memoryHistoryDao);
            memoryHistoryDao.forwardHistory();
        }
        stockRepository.save(stock);
    }
}
