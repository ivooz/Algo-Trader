package com.gft.removing;

import com.gft.config.Application;
import com.gft.repository.InsufficientDataException;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.gft.service.creating.NewStockService;
import com.gft.service.removing.StockRemovingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Created by iozi on 20/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class RemovingStockTest {

    @Autowired
    StockRemovingService stockRemovingService;

    @Autowired
    NewStockService newStockService;

    @Autowired
    StockRepository stockRepository;

    private final static String TICKER = "FL";

    @Test
    public void testAddingThenDeleting() {
        try {
            newStockService.addNewStock(TICKER);
            stockRemovingService.remove(TICKER);
            assertNull(stockRepository.findOne(TICKER));
        } catch (DataAccessException | InsufficientDataException e) {
            fail();
            e.printStackTrace();
        }

    }

}
