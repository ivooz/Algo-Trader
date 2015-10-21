package com.gft.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.gft.config.Application;
import com.gft.model.db.Stock;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class NewTickerHintTest {

	@Autowired
	StockRepository stockRepository;

	@Autowired
	StockHistoryRepository stockHistoryRepository;

	@After
	public void clean() {
		stockRepository.deleteAll();
	}

	@Before
	public void beforeClean() {
		stockHistoryRepository.deleteAll();
		stockRepository.deleteAll();
	}

	@Test
	public void cheeckingRestExistingStocksInDB() {
		RestTemplate restTemplate = new RestTemplate();

		Stock stock2 = new Stock();
		stock2.setTicker("PKN");
		stock2.setFullName("PKNORLEN");
		stockRepository.save(stock2);

		List<String> apiResponse = restTemplate
				.getForObject("http://localhost:60001/tickers", List.class);

		System.out.println(apiResponse);
		assertTrue(apiResponse.toString().contains("PKN"));
		assertEquals(apiResponse.size(), 1);

	}

	@Test
	public void cheeckingRestNotExistingStocksInDB() {
		Stock stock1 = new Stock();
		stock1.setTicker("LOXO");
		stock1.setFullName("Loxo Oncology");
		stockRepository.save(stock1);

		RestTemplate restTemplate = new RestTemplate();
		List<String> apiResponse = restTemplate.getForObject(
				"http://localhost:60001/tickersAvailable", List.class);

		assertEquals(false, apiResponse.contains("LOXO"));
		assertEquals(true, apiResponse.toString().contains("MSFT"));

	}

}
