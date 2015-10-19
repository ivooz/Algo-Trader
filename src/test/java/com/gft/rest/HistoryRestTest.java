package com.gft.rest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.gft.config.Application;
import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class HistoryRestTest {

	@Autowired
	StockHistoryRepository stockHistoryRepository;
	@Autowired
	StockRepository stockRepository;

	@After
	public void clean() {

		stockHistoryRepository.deleteAll();
		stockRepository.deleteAll();
	}

	@Test
	public void checkingRestStockHistory() {

		Stock testStock = new Stock();
		testStock.setFullName("KGHM");
		testStock.setTicker("KGH");

		StockHistory stockHistory = new StockHistory();
		stockHistory.setId(1l);
		stockHistory.setDate(new Date());
		stockHistory.setStock(testStock);
		stockHistory.setOpeningPrice(new BigDecimal(46.30));
		stockHistory.setClosingPrice(new BigDecimal(47.40));
		stockHistory.setLowPrice(new BigDecimal(46.20));
		stockHistory.setHighPrice(new BigDecimal(47.60));
		stockHistory.setVolume(123l);

		stockRepository.save(testStock);
		stockHistoryRepository.save(stockHistory);
		RestTemplate restTemplate = new RestTemplate();

		ArrayList<Object> apiResponse = restTemplate.getForObject("http://localhost:60001/history/KGH",
				ArrayList.class);

		assertEquals(true, apiResponse.toString().contains("fullName=KGHM"));
		assertEquals(true, apiResponse.toString().contains("ticker=KGH"));
		assertEquals(true, apiResponse.toString().contains("closingPrice=47.4"));
		assertEquals(false, apiResponse.toString().contains("fullName=LOTOS"));

	}

}
