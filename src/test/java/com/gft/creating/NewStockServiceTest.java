package com.gft.creating;

import com.gft.component.ListAlgorithmWrapper;
import com.gft.config.Application;
import com.gft.config.HibernateConfig;
import com.gft.config.WebConfig;
import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.gft.service.creating.NewStockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Created by iozi on 16/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class,HibernateConfig.class, WebConfig.class})
@WebAppConfiguration
public class NewStockServiceTest {

    @Autowired
    NewStockService newStockService;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ListAlgorithmWrapper listAlgorithmWrapper;

    @Autowired
    AlgorithmRepository algorithmRepository;

    private final String ticker = "MSFT";

    @Before
    public void init() {
    }

    @Test
    public void testAddingNewTicker() {
        try {
            newStockService.addNewStock(ticker);
        } catch (DataAccessException | InsufficientDataException e) {
            e.printStackTrace();
            fail();
        }
        Stock stock = stockRepository.findByIdAndFetchAlgorithmsEagerly(ticker);
        assertEquals(listAlgorithmWrapper.getAlgorithms().size(), stock.getAlgorithms().size());
    }


}
