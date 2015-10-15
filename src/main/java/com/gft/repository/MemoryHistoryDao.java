package com.gft.repository;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.service.DataAccessException;
import com.gft.service.DataDownloadService;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * Used in NewStockService
 * <p>
 * Created by iozi on 14/10/2015.
 */
@Repository("memoryHistoryDao")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class MemoryHistoryDao implements HistoryDAO {

    private static final Logger logger = LoggerFactory.getLogger(MemoryHistoryDao.class);

    private List<StockHistory> historyList;

    @Autowired
    DataDownloadService dataDownloadService;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    private int timesInvoked = 0;

    /**
     * Starts from the first day in history as the 'current day', each invocation shifts the current date forward.
     * @param stock
     * @param days including the current day (5 = 4 previous days + today)
     * @return
     * @throws InsufficientDataException
     * @throws DataAccessException
     * @throws InvalidArgumentException
     */
    @Override
    public List<StockHistory> obtainStockHistoryForPeriod(Stock stock, int days) throws InsufficientDataException,
            DataAccessException, InvalidArgumentException {
        if (historyList == null) {
            historyList = dataDownloadService.downloadHistoricalData(stock);
            logger.info("Saving downloaded history");
            stockHistoryRepository.save(historyList);
        }
        if (days <= 0) {
            throw new InvalidArgumentException(new String[]{"Requested interval cannot be smaller than 1!", String.valueOf(days)});
        }
        if (timesInvoked - days + 1 < 0) {
            logger.error(INSUFFICIENT_INTERVAL);
            timesInvoked++;
            throw new InsufficientDataException(INSUFFICIENT_INTERVAL);
        }
        List<StockHistory> requestedInterval = historyList.subList(timesInvoked - days + 1, timesInvoked + 1);
        timesInvoked++;
        return requestedInterval;
    }
}
