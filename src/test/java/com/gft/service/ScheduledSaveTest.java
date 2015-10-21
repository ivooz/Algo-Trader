package com.gft.service;

import com.gft.config.Application;
import com.gft.model.db.Algorithm;
import com.gft.model.db.AlgorithmHistory;
import com.gft.model.db.Stock;
import com.gft.repository.InsufficientDataException;
import com.gft.repository.data.AlgorithmHistoryRepository;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;
import com.gft.service.creating.NewStockService;
import com.gft.service.updating.AlgorithmHistoryUpdateService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class ScheduledSaveTest {

	public static final String TICKER = "MSFT";

	@Autowired
	StockRepository stockRepo;

	@Autowired
	AlgorithmRepository algorithmRepository;

	@Autowired
	AlgorithmHistoryUpdateService hs;

	@Autowired
	AlgorithmHistoryRepository algorithmHistoryRepository;

	@Autowired
	NewStockService newStockService;

	@Test
	public void TestIfZeroes() {
		try {
			newStockService.addNewStock(TICKER);
			hs.saveAlgorithmStatistics();
			Stock alg1 = stockRepo.findByIdAndFetchAlgorithmsEagerly(TICKER);
			Iterator<Algorithm> alg3 = alg1.getAlgorithms().iterator();
			while (alg3.hasNext()) {
				Assert.assertEquals(0.0, alg3.next().getAbsoluteGain());
			}
		} catch (DataAccessException | InsufficientDataException e) {
			e.printStackTrace();
			fail();
		}
	}

//	@Test
//	public void TestIfSaves() {
//		try {
//			newStockService.addNewStock(TICKER);
//			hs.saveAlgorithmStatistics();
//			List<AlgorithmHistory> reposlit = algorithmHistoryRepository.findAll();
//			Iterator<AlgorithmHistory> it = reposlit.iterator();
//			while (it.hasNext()) {
//				AlgorithmHistory history = it.next();
//				Assert.assertEquals(1.4, history.getAbsoluteGain());
//				Assert.assertEquals(1.12, history.getAggregateGain());
//			}
//		} catch (DataAccessException | InsufficientDataException e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
}
