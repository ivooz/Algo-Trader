package com.gft.repository;

import com.gft.config.Application;
import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.sun.javaws.exceptions.InvalidArgumentException;
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

import java.util.List;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by iozi on 14/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class HistoryDaoTest {

    private static final Logger logger = LoggerFactory.getLogger(HistoryDaoTest.class);

    @Autowired
    @Qualifier("databaseHistoryDao")
    HistoryDAO databaseHistoryDao;

    @Autowired
    @Qualifier("memoryHistoryDao")
    HistoryDAO memoryHistoryDao;

    @Autowired
    StockRepository stockRepository;

    Stock stock = new Stock();

    @Before
    public void init() {
        stock.setTicker("MSFT");
        stockRepository.save(stock);
    }

    @Test
    public void testProperInjection() {
        assertNotNull(databaseHistoryDao);
        assertTrue(databaseHistoryDao instanceof DatabaseHistoryDao);
        assertNotNull(memoryHistoryDao);
        assertTrue(memoryHistoryDao instanceof MemoryHistoryDao);
    }

    @Test
    public void testMemoryDaoIntervalSafeguard() {
        for(int i=0; i<10;i++) {
            try {
                List<StockHistory> historyList = memoryHistoryDao.obtainStockHistoryForPeriod(stock,11);
                //Fail if not thrown
                fail();
            } catch (InvalidArgumentException | InsufficientDataException | DataAccessException ex) {
            }
        }
        try {
            List<StockHistory> historyList = memoryHistoryDao.obtainStockHistoryForPeriod(stock,10);
            assertEquals(10,historyList.size());
        } catch (InvalidArgumentException | InsufficientDataException | DataAccessException ex) {
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
        } catch (InvalidArgumentException | InsufficientDataException | DataAccessException ex) {
            logger.error("Test failed!",ex);
            fail();
        }
        assertEquals(10, stockHistories.size());
    }
}
