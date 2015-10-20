package com.gft.service.creating;

import com.gft.aspect.Log;
import com.gft.model.db.Stock;
import com.gft.repository.MemoryHistoryDao;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.gft.service.downloading.DataDownloadService;
import com.gft.service.updating.AlgorithmHistoryUpdateService;
import com.gft.service.updating.StatisticsUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by iozi on 15/10/2015.
 */
@Service
public class NewStockServiceImpl implements NewStockService {

    private static final Logger logger = LoggerFactory.getLogger(NewStockServiceImpl.class);

    @Autowired
    DataDownloadService dataDownloadService;

    @Autowired
    StatisticsUpdateService updateService;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    DataDownloadService downloadService;

    @Autowired
    StockHistoryRepository stockHistoryRepository;

    @Autowired
    AlgorithmHistoryUpdateService historyUpdateService;

    @Log
    @Override
    public void addNewStock(String ticker) throws DataAccessException, InsufficientDataException {
        if(stockRepository.findOne(ticker)!=null) {
            //TODO throw exception
            return;
        }
        MemoryHistoryDao memoryHistoryDao = new MemoryHistoryDao(dataDownloadService,stockHistoryRepository);
        Stock stock = dataDownloadService.downloadNewStock(ticker);
        stockRepository.save(stock);
        Date historyHead =  memoryHistoryDao.getCurrentDay(stock).getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(historyHead);
        
        int nextUpdate = cal.get(Calendar.MONTH) < Calendar.JULY ? Calendar.JULY : Calendar.JANUARY;
        for (int i = 0; i < memoryHistoryDao.getHistorySize(stock); i++) {
            historyHead = memoryHistoryDao.getCurrentDay(stock).getDate();
            cal.setTime(historyHead);
            if(cal.get(Calendar.MONTH) == nextUpdate ) {
                historyUpdateService.saveAlgorithmStatistics(historyHead,stock.getTicker());
                nextUpdate = (nextUpdate+6)%12;
            }
        
            updateService.updateStatistics(stock, historyHead, memoryHistoryDao);
            memoryHistoryDao.forwardHistory();
        }
        stockRepository.save(stock);
    }
}
