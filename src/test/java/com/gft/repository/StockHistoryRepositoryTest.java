package com.gft.repository;

import com.gft.config.Application;
import com.gft.model.db.Stock;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.gft.service.creating.NewStockService;
import com.gft.service.updating.DailyUpdateService;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by iozi on 21/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class StockHistoryRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(StockHistoryRepositoryTest.class);
    private static final String TICKER = "CSV";

    @Autowired
    private NewStockService newStockService;

    @Autowired
    private DailyUpdateService dailyUpdateService;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @Autowired
    private StockRepository stockRepository;

    @Test
    @Transactional
    public void testDownloadingLatestDate()  {
        try {
            newStockService.addNewStock(TICKER);
            Stock stock = stockRepository.findOne(TICKER);
            dailyUpdateService.updateStocks();
            Date date = stockHistoryRepository.findFirst1ByStockOrderByDateDesc(stock).getDate();
            assertTrue(DateUtils.isSameDay(date, new Date()));
        } catch (DataAccessException | InsufficientDataException e) {
            e.printStackTrace();
            fail();
        }
    }
}
