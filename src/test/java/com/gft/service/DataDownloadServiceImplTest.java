package com.gft.service;

import com.gft.config.Application;
import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by iozi on 13/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class DataDownloadServiceImplTest {

    @Autowired
    DataDownloadService sut;

    List<Stock> stocks = Arrays.asList(new Stock("MSFT"),new Stock("CSV"));

    @Test
    public void testDownloadingCurrentData() {
        stocks.stream().forEach(s -> {
            try {
                StockHistory stockHistory = sut.downloadCurrentData(s);
                assertNotNull(stockHistory);
                assertNotNull(stockHistory.getDate());
                assertNotNull(stockHistory.getStock());
                assertNotNull(stockHistory.getOpeningPrice());
                assertNotNull(stockHistory.getClosingPrice());
                assertNotNull(stockHistory.getLowPrice());
                assertNotNull(stockHistory.getHighPrice());
                assertNotEquals(0,stockHistory.getVolume());
            } catch (DataAccessException e) {
                e.printStackTrace();
                fail();
            }
        });
    }

    @Test
    public void testDownloadingHistoricalData() {
        stocks.stream().forEach(s -> {
            try {
                List<StockHistory> stockHistoryList = sut.downloadHistoricalData(s);
                assertNotEquals(0,stockHistoryList.size());
                stockHistoryList.stream().forEach(stockHistory -> {
                    assertNotNull(stockHistory);
                    assertNotNull(stockHistory.getDate());
                    assertNotNull(stockHistory.getStock());
                    assertNotNull(stockHistory.getOpeningPrice());
                    assertNotNull(stockHistory.getClosingPrice());
                    assertNotNull(stockHistory.getLowPrice());
                    assertNotNull(stockHistory.getHighPrice());
                });
            } catch (DataAccessException e) {
                e.printStackTrace();
                fail();
            }
        });
    }
}