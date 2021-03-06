package com.gft.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gft.config.Application;
import com.gft.model.db.Algorithm;
import com.gft.model.db.AlgorithmHistory;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmHistoryRepository;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;
import com.gft.service.updating.AlgorithmHistoryUpdateService;

import junit.framework.Assert;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class ScheduledWithDateTest {

	public static final String TICKER = "MSFT";

	@Autowired
	StockRepository stockRepository;
	@Autowired
	AlgorithmRepository algorithmRepository;
	@Autowired
	AlgorithmHistoryUpdateService algorithmHistoryUpdateService;
	@Autowired
	AlgorithmHistoryRepository algorithmHistoryRepository;

	@Before
	public void clean() {
		algorithmHistoryRepository.deleteAll();
	}

	@Before
	public void init() {

		Stock stock = new Stock();
		stock.setFullName("MICROSOFT");
		stock.setTicker(TICKER);
		stock.setType("equity");
		algorithmHistoryRepository.deleteAll();

		Algorithm alg = new Algorithm();

		alg.setName("Srednia");
		alg.setStock(stock);
		alg.setAbsoluteGain(1.4);
		alg.setAggregateGain(1.14);
		List<Algorithm> algs = new ArrayList<>();
		algs.add(alg);
		stock.setAlgorithms(algs);
		stockRepository.save(stock);
		stockRepository.flush();
		algorithmRepository.save(alg);
		algorithmRepository.flush();

		Calendar cal = Calendar.getInstance();
		cal.set(2012, 07, 07, 3, 13, 55);
		Date date = cal.getTime();
		Stock stock1 = new Stock();
		stock1.setTicker(TICKER);
		algorithmHistoryUpdateService.saveAlgorithmStatistics(date, stock1);
	}

	@Test
	public void TestIfSavesWithDateforTicker() {

		List<AlgorithmHistory> reposlit = algorithmHistoryRepository.findAll();
		Iterator<AlgorithmHistory> it = reposlit.iterator();
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 07, 07, 3, 13, 55);
		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		while (it.hasNext()) {
			AlgorithmHistory history = it.next();
			Assert.assertEquals(dateFormat.format(date),
					dateFormat.format(history.getDate()));
		}
	}
}
