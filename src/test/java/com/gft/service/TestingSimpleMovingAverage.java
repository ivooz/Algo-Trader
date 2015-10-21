package com.gft.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gft.component.MovingAverage;
import com.gft.config.Application;
import com.gft.model.Action;
import com.gft.model.db.Stock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class TestingSimpleMovingAverage {
	
	@Autowired
	MovingAverage simpleMovingAverage;
	
	@Autowired
	@Qualifier("simpleMovingAverages")
	ArrayList<MovingAverage> simpleMovingAverages;
	
	@Test
	public void checkingCorrectnessOfSimpleMovingAverageForRaisingPrice() {
		HistoryDAOmock historyDaoMock = new HistoryDAOmock();
		historyDaoMock.setRisingPrice(10); // we are sure that price is rising
		Action action = simpleMovingAverage.predict(new Date(), new Stock(),
				historyDaoMock);

		assertEquals(Action.BUY, action);
	}

	@Test
	public void checkingCorrectnessOfSimpleMovingAverageForDecreasingPrice() {
		HistoryDAOmock historyDaoMock = new HistoryDAOmock();
		historyDaoMock.setDecrisingPrice(10); // we are sure that price is decrising
		Action action = simpleMovingAverage.predict(new Date(), new Stock(),
				historyDaoMock);
	
		assertEquals(Action.SELL, action);
	}
	
	@Test
	public void checkingCorrectnessOfInjectionArrayBean(){
		assertNotNull(simpleMovingAverages);
	}
	
}
