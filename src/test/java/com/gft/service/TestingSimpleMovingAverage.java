package com.gft.service;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gft.component.SimpleMovingAverage;
import com.gft.config.Application;
import com.gft.model.Action;
import com.gft.model.db.Stock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
public class TestingSimpleMovingAverage {

	@Test
	public void checkingCorrectnessOfSimpleMovingAverageForRaisingPrice() {
		HistoryDAOmock historyDaoMock = new HistoryDAOmock();
		historyDaoMock.setRisingPrice(10); // we are sure that price is rising
		SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage(10);
		Action action = simpleMovingAverage.predict(GregorianCalendar.getInstance().getTime(), new Stock(),
				historyDaoMock);

		assertEquals(Action.BUY, action);
	}

	@Test
	public void checkingCorrectnessOfSimpleMovingAverageForDecreasingPrice() {
		HistoryDAOmock historyDaoMock = new HistoryDAOmock();
		historyDaoMock.setDecrisingPrice(10); // we are sure that price is decrising
		SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage(5);
		Action action = simpleMovingAverage.predict(GregorianCalendar.getInstance().getTime(), new Stock(),
				historyDaoMock);
	

		assertEquals(Action.SELL, action);
	}
}
