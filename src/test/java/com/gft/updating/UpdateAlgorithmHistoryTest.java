package com.gft.updating;

import com.gft.component.ListAlgorithmWrapper;
import com.gft.config.Application;
import com.gft.config.HibernateConfig;
import com.gft.config.WebConfig;
import com.gft.model.db.AlgorithmHistory;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmHistoryRepository;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.gft.service.creating.NewStockService;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Calendar;

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

    private final static String ticker = "FB";

    @Before
    public void clean() {
        algorithmHistoryRepository.deleteAll();
    }

    @Test
    public void testAddingNewTicker() {
        try {
            newStockService.addNewStock(ticker);
            algorithmHistoryRepository.findAll().stream().forEach(h -> {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(h.getDate());
                int month = calendar.get(Calendar.MONTH);
                TestCase.assertTrue(month == Calendar.JANUARY || month == Calendar.JULY);
            });
        } catch (DataAccessException | InsufficientDataException e) {
            e.printStackTrace();
            fail();
        }
    }
}
