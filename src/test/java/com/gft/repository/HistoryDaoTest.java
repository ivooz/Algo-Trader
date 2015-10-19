package com.gft.repository;

import com.gft.config.Application;
import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.gft.service.downloading.DataDownloadService;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

/**
 * Created by iozi on 14/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class HistoryDaoTest {

    private static final Logger logger = LoggerFactory.getLogger(HistoryDaoTest.class);

    @Autowired
    DataDownloadService dataDownloadService;

    @Autowired
    StockHistoryRepository stockHistoryRepository;

    @Autowired
    @Qualifier("databaseHistoryDao")
    HistoryDAO databaseHistoryDao;

    ForwardableHistoryDAO memoryHistoryDao;

    @Autowired
    StockRepository stockRepository;

    Stock stock = new Stock();

    @Before
    public void init() {
        memoryHistoryDao = new MemoryHistoryDao(dataDownloadService,stockHistoryRepository);
        stock.setTicker("MSFT");
        stock.setAlgorithms(new ArrayList<>());
        stock.setType("equity");
        stock.setFullName("Microsoft");
        stockRepository.save(stock);
    }

    @Test
    public void testMemoryDaoIntervalSafeguard() {
        for(int i=0; i<9;i++) {
            try {
                memoryHistoryDao.forwardHistory();
                List<StockHistory> historyList = memoryHistoryDao.obtainStockHistoryForPeriod(stock,11);
                fail();
            } catch (InsufficientDataException | DataAccessException ex) {
            }
        }
        try {
            List<StockHistory> historyList = memoryHistoryDao.obtainStockHistoryForPeriod(stock,10);
            assertEquals(10,historyList.size());
        } catch (InsufficientDataException | DataAccessException ex) {
            logger.error("Test failed!",ex);
            fail();
        }
    }

    @Test
    public void testDatabaseDao() {
        List<StockHistory> stockHistories = null;
        try {
            memoryHistoryDao.obtainStockHistoryForPeriod(stock,1);
            stockHistories = databaseHistoryDao.obtainStockHistoryForPeriod(stock, 10);
        } catch (InsufficientDataException | DataAccessException ex) {
            logger.error("Test failed!",ex);
            fail();
        }
        assertEquals(10, stockHistories.size());
    }

    //TODO update tests below

//    @Test
    public void testCurrentDaysMemoryHistoryDao() {
        try {
            Date date = memoryHistoryDao.getCurrentDay(stock).getDate();
            List<StockHistory> stockHistories = memoryHistoryDao.obtainStockHistoryForPeriod(stock, 1);
            assertTrue(DateUtils.isSameDay(stockHistories.get(0).getDate(), date));
            stockHistories = memoryHistoryDao.obtainStockHistoryForPeriod(stock, 1);
            assertFalse(DateUtils.isSameDay(stockHistories.get(0).getDate(), date));
        } catch (InsufficientDataException | DataAccessException ex) {
            logger.error("Test failed!", ex);
            fail();
        }
    }

//    @Test
    public void testCurrentDaysDatabaseHistoryDao() {
        try {
            memoryHistoryDao.obtainStockHistoryForPeriod(stock, 1);
            assertTrue(DateUtils.isSameDay(databaseHistoryDao.getCurrentDay(stock).getDate(),
                    databaseHistoryDao.obtainStockHistoryForPeriod(stock, 1).get(0).getDate()));
        } catch (InsufficientDataException | DataAccessException ex) {
            logger.error("Test failed!", ex);
            fail();
        }
    }
}
