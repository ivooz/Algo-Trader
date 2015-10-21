package com.gft.updating;

import com.gft.config.Application;
import com.gft.config.HibernateConfig;
import com.gft.config.WebConfig;
import com.gft.model.db.Algorithm;
import com.gft.model.db.AlgorithmHistory;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmHistoryRepository;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.InsufficientDataException;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.gft.service.creating.NewStockService;
import com.gft.service.updating.AlgorithmHistoryUpdateService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * Created by iozi on 16/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, HibernateConfig.class, WebConfig.class})
@WebAppConfiguration
public class UpdateAlgorithmHistoryTest {

    @Autowired
    NewStockService newStockService;

    @Autowired
    AlgorithmHistoryRepository algorithmHistoryRepository;

    @Autowired
    AlgorithmHistoryUpdateService algorithmHistoryUpdateService;

    @Autowired
    AlgorithmRepository algorithmRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockHistoryRepository stockHistoryRepository;

    private final static String TICKER = "CSV";
    public static final String TESTED_ALGORITHM_NAME = "SimpleMovingAverage5";

    @Before
    public void clean() {
//        algorithmHistoryRepository.deleteAll();
//        algorithmHistoryRepository.flush();
//        algorithmRepository.deleteAll();
//        stockRepository.deleteAll();
    }

    @Test
    public void testAddingNewTicker() {
        try {
            newStockService.addNewStock(TICKER);
            List<AlgorithmHistory> historyList = algorithmHistoryRepository.findAll();
            assertTrue(historyList.size() > 10);
        } catch (DataAccessException | InsufficientDataException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void testExtraDayAdded() {
        try {
            newStockService.addNewStock(TICKER);
            List<AlgorithmHistory> histories = algorithmHistoryRepository.findByTickerAndAlgorithmName(TESTED_ALGORITHM_NAME, TICKER);
            int initialSize = algorithmHistoryRepository.findByTickerAndAlgorithmName(TESTED_ALGORITHM_NAME, TICKER).size();
            algorithmHistoryUpdateService.saveAlgorithmStatistics();
            histories = algorithmHistoryRepository.findByTickerAndAlgorithmName(TESTED_ALGORITHM_NAME, TICKER);
            int finalSize = algorithmHistoryRepository.findByTickerAndAlgorithmName(TESTED_ALGORITHM_NAME, TICKER).size();
            TestCase.assertEquals(initialSize + 1, finalSize);
        } catch (DataAccessException | InsufficientDataException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void testRebuy() {
        try {
            newStockService.addNewStock(TICKER);
            Stock stock = stockRepository.findOne(TICKER);
            BigDecimal priceBought = stockHistoryRepository.findFirst1ByStockOrderByDateDesc(stock).getClosingPrice();
            algorithmHistoryUpdateService.saveAlgorithmStatistics();
            Algorithm algorithm = algorithmRepository.findByNameandTicker(TESTED_ALGORITHM_NAME, TICKER);
            assertEquals(priceBought, algorithm.getPriceBought());
        } catch (DataAccessException | InsufficientDataException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAlgorithmsReset() {
        try {
            newStockService.addNewStock(TICKER);
            algorithmHistoryUpdateService.saveAlgorithmStatistics(new Date(), stockRepository.findByIdAndFetchAlgorithmsEagerly(TICKER));
            Stock stock = stockRepository.findByIdAndFetchAlgorithmsEagerly(TICKER);
            stock.getAlgorithms().stream().forEach(a -> {
                assertEquals(0d, a.getAbsoluteGain());
                assertEquals(1d, a.getAggregateGain());
            });
        } catch (DataAccessException | InsufficientDataException e) {
            e.printStackTrace();
            fail();
        }
    }
}
