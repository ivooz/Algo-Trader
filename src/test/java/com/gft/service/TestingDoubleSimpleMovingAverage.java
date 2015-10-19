package com.gft.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gft.component.DoubleSimpleMovingAverage;
import com.gft.config.Application;
import com.gft.model.Action;
import com.gft.model.db.Stock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class TestingDoubleSimpleMovingAverage {
	
	@Autowired
	DoubleSimpleMovingAverage  doubleSimpleMovingAverage;
	
	
	@Test
	@Ignore
	public void checkingCorrectnessOfDoubleSimpleMovingAverageForRaisingPrice(){
		HistoryDAOmock historyDaoMock = new HistoryDAOmock();
		historyDaoMock.setRisingPrice(30);
		Action action = doubleSimpleMovingAverage.predict(new Date(),new Stock(), historyDaoMock);
		assertEquals(Action.BUY, action);
	}
	

}
