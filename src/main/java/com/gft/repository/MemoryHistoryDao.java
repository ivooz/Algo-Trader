package com.gft.repository;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.service.DataAccessException;
import com.gft.service.downloading.DataDownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MemoryHistoryDao implements ForwardableHistoryDAO {

    private static final Logger logger = LoggerFactory.getLogger(MemoryHistoryDao.class);

    private List<StockHistory> historyList;
    private int currentDay = 0;
    private final DataDownloadService dataDownloadService;
    private final StockHistoryRepository stockHistoryRepository;

    public MemoryHistoryDao(DataDownloadService dataDownloadService, StockHistoryRepository stockHistoryRepository) {
        this.dataDownloadService = dataDownloadService;
        this.stockHistoryRepository = stockHistoryRepository;
    }

    /**
     * Starts from the first day in history as the 'current day', each invocation shifts the current date forward.
     *
     * @param stock
     * @param days  including the current day (5 = 4 previous days + today)
     * @return
     * @throws InsufficientDataException
     * @throws DataAccessException
     */
    @Override
    public List<StockHistory> obtainStockHistoryForPeriod(Stock stock, int days) throws InsufficientDataException,
            DataAccessException {
        historyNullGuard(stock);
        if (days <= 0) {
            throw new DataAccessException("Requested interval cannot be smaller than 1!");
        }
        if (currentDay - days + 1 < 0) {
            throw new InsufficientDataException(INSUFFICIENT_INTERVAL);
        }
        List<StockHistory> requestedInterval = historyList.subList(currentDay - days + 1, currentDay + 1);
        return requestedInterval;
    }

    @Override
    public StockHistory getCurrentDay(Stock stock) throws InsufficientDataException, DataAccessException {
        historyNullGuard(stock);
        return historyList.get(currentDay);
    }

    @Override
    public int getHistorySize(Stock stock) throws InsufficientDataException, DataAccessException {
        historyNullGuard(stock);
        return historyList.size();
    }

    @Override
    public void forwardHistory() {
        currentDay++;
    }

    private void historyNullGuard(Stock stock) throws DataAccessException {
        if (historyList == null) {
            historyList = dataDownloadService.downloadHistoricalData(stock);
            historyList.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
            logger.info("Saving downloaded history");
            stockHistoryRepository.save(historyList);
        }
    }
}
