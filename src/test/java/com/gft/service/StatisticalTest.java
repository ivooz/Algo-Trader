package com.gft.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gft.component.PredictionAlgorithm;
import com.gft.component.SimpleMovingAverage;
import com.gft.config.Application;
import com.gft.model.Action;
import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.HistoryDAO;

import junit.framework.Assert;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class StatisticalTest {

	@Autowired
	StatisticsUpdateServiceImpl sds;
	@Test
	public void testThatAlgorithmBuys()
	{

		HistoryDAOmock hdom = new HistoryDAOmock();
		hdom.setRisingPrice(1000);
		Stock stock = new Stock();
		stock.setFullName("KGHM");
		
	
		stock.setTicker("KGH");
		Date date = new Date(2015,10,15);
		sds.updateStatistics(stock, date, hdom);
	
Assert.assertFalse(stock.getAlgorithms().get(0).getPriceBought().equals(0));
	
}
	@Test
	public void testThatAlgorithmSells()
	{
		HistoryDAOmock hdom = new HistoryDAOmock();
		hdom.setRisingPrice(1000);
		HistoryDAOmock hdom1 = new HistoryDAOmock();
		hdom1.setDecrisingPrice(1000);
		Stock stock = new Stock();
		stock.setFullName("KGHM");
		
	
		stock.setTicker("KGH");
		Date date = new Date(2015,10,15);
		sds.updateStatistics(stock, date, hdom);
		sds.updateStatistics(stock, date, hdom1);
		
		Assert.assertTrue(stock.getAlgorithms().get(0).getPriceBought().equals(BigDecimal.ZERO));
}
	@Test
	public void testThatAlgorithmEarns()
	{
		HistoryDAOmock hdom = new HistoryDAOmock();
		hdom.setRisingPrice(1000);
		HistoryDAOmock hdom1 = new HistoryDAOmock();
		hdom1.setDecrisingPrice(1000);
		Stock stock = new Stock();
		stock.setFullName("KGHM");
		
	
		stock.setTicker("KGH");
		Date date = new Date(2015,10,15);
		sds.updateStatistics(stock, date, hdom);
	
		sds.updateStatistics(stock, date, hdom1);
	
		Assert.assertEquals(1.0,stock.getAlgorithms().get(0).getAbsoluteGain());
		Assert.assertEquals(1.0,stock.getAlgorithms().get(0).getAggregateGain());
}
}