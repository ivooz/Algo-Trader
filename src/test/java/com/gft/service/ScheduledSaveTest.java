package com.gft.service;

import java.util.ArrayList;
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
import com.gft.service.updating.ScheduledAlgorithmHistorySave;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class ScheduledSaveTest {
	@Autowired
	StockRepository stockRepo;
	@Autowired
	AlgorithmRepository algRepo;
	@Autowired
	ScheduledAlgorithmHistorySave hs;
	@Autowired
	AlgorithmHistoryRepository shr;
	@Before
	public void init() {

		Stock stock = new Stock();
		stock.setFullName("MICROSOFT");
		stock.setTicker("MSFT");
		stock.setType("equity");

		Algorithm alg = new Algorithm();

		alg.setName("Srednia");
		alg.setStock(stock);
		alg.setAbsoluteGain(1.4);
		alg.setAggregateGain(1.12);
		List<Algorithm> algs = new ArrayList<>();
		algs.add(alg);
		stock.setAlgorithms(algs);
		stockRepo.save(stock);
		stockRepo.flush();

		hs.saveAlgorithmStatistics();

	}
	@Test
	public void TestIfZeroes() {

		Stock alg1 = stockRepo.findByIdAndFetchAlgorithmsEagerly("MSFT");
		Iterator<Algorithm> alg3 = alg1.getAlgorithms().iterator();
		while (alg3.hasNext()) {
			Assert.assertEquals(0.0, alg3.next().getAbsoluteGain());
		}
	}
	@Test
	public void TestIfSaves() {
		List<AlgorithmHistory> reposlit = shr.findAll();
		Iterator<AlgorithmHistory> it = reposlit.iterator();

		while (it.hasNext()) {

			AlgorithmHistory history = it.next();

			Assert.assertEquals(1.4, history.getAbsoluteGain());
			Assert.assertEquals(1.12, history.getAggregateGain());
		}
	}

}
