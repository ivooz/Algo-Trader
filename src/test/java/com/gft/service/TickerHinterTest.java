package com.gft.service;

import com.gft.service.parsing.ParsingException;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gft.config.Application;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;

import static junit.framework.Assert.*;

/**
 * Created by iozi on 06/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class TickerHinterTest {

	private static final Logger logger = LoggerFactory.getLogger(TickerHinterTest.class);

	@Autowired
	private JdbcTemplate namedJdbcTemplate;
	@Autowired
	StockRepository stockRepository;
	@Autowired
	AlgorithmRepository algorithmRepository;
	@Autowired
	NewTickerHinter tickerHinter;

	@Test
	public void test() throws ClassNotFoundException {
		Generator gen = new Generator();
		stockRepository.save(gen.generateStocks(10));
		assertNotNull(stockRepository.findAll());
	}

	@Test
	public void hinterTest() throws ClassNotFoundException {
		Stock stock = new Stock();
		stock.setTicker("MSFT");
		stockRepository.save(stock);
		stockRepository.flush();
		String json = null;
		try {
			json = new Gson().toJson(tickerHinter.hintNotPickedTickers()) ;
		} catch (ParsingException ex) {
			logger.error("TEST FAILED",ex);
			fail();
		}
		assertFalse(json.contains("MSFT"));
		assertTrue(json.contains("MICT"));
	}
}
