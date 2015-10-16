package com.gft.service;

import com.gft.config.Application;
import com.gft.model.db.Stock;
import com.gft.repository.HistoryDAO;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockRepository;
import com.gft.service.updating.StatisticsUpdateService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class StatisticalTest {

	@Autowired
	StatisticsUpdateService sds;

	@Autowired
	@Qualifier("memoryHistoryDao")
	HistoryDAO historyDAO;

	@Autowired
	StockRepository stockRepository;

	private Stock stock;

	@Before
	public void init() {
		stock = new Stock();
		stock.setTicker("MSFT");
		stockRepository.save(stock);
		for (int i = 0; i < 500; i++) {
			try {
				historyDAO.obtainStockHistoryForPeriod(stock, 1);
			} catch (InsufficientDataException | DataAccessException ex) {
			}
		}

	}

	@Test
	public void testIntegrationWithMemoryHistory() {
		try {
			Date date = historyDAO.getCurrentDay(stock).getDate();
			sds.updateStatistics(stock, date, historyDAO);
			assertTrue(stock.getAlgorithms().size()>0);
		} catch (InsufficientDataException | DataAccessException e) {
			fail();
			e.printStackTrace();
		}
	}

//	@Test
	public void testThatAlgorithmBuys() {
		HistoryDAOmock hdom = new HistoryDAOmock();
		hdom.setRisingPrice(1000);
		Stock stock = new Stock();
		stock.setFullName("KGHM");
		stock.setTicker("KGH");
		Date date = new Date(2015, 10, 15);
		sds.updateStatistics(stock, date, hdom);

		Assert.assertFalse(
				stock.getAlgorithms().get(0).getPriceBought().equals(0));
	}

//	@Test
	public void testThatAlgorithmSells() {
		HistoryDAOmock hdom = new HistoryDAOmock();
		hdom.setRisingPrice(1000);
		HistoryDAOmock hdom1 = new HistoryDAOmock();
		hdom1.setDecrisingPrice(1000);
		Stock stock = new Stock();
		stock.setFullName("KGHM");
		stock.setTicker("KGH");
		Date date = new Date(2015, 10, 15);
		sds.updateStatistics(stock, date, hdom);
		sds.updateStatistics(stock, date, hdom1);

		Assert.assertTrue(stock.getAlgorithms().get(0).getPriceBought()
				.equals(BigDecimal.ZERO));
	}

//	@Test
	public void testThatAlgorithmEarns() {
		HistoryDAOmock hdom = new HistoryDAOmock();
		hdom.setRisingPrice(1000);
		HistoryDAOmock hdom1 = new HistoryDAOmock();
		hdom1.setDecrisingPrice(1000);
		Stock stock = new Stock();
		stock.setFullName("KGHM");
		stock.setTicker("KGH");
		Date date = new Date(2015, 10, 15);
		sds.updateStatistics(stock, date, hdom);

		sds.updateStatistics(stock, date, hdom1);

		Assert.assertEquals(1.0,
				stock.getAlgorithms().get(0).getAbsoluteGain());
		Assert.assertEquals(1.0,
				stock.getAlgorithms().get(0).getAggregateGain());
	}
}