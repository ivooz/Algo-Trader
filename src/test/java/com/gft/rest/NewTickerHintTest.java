package com.gft.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.gft.config.Application;
import com.gft.model.db.Stock;
import com.gft.repository.data.StockRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class NewTickerHintTest {

	@Autowired
	StockRepository stockRepository;

	@Test
	@Ignore
	public void cheeckingRestExistingStocksInDB() {
		Stock stock1 = new Stock();
		stock1.setTicker("KGH");
		stock1.setFullName("KGHM");
		stockRepository.save(stock1);

		Stock stock2 = new Stock();
		stock2.setTicker("PKN");
		stock2.setFullName("PKNORLEN");
		stockRepository.save(stock2);

		RestTemplate restTemplate = new RestTemplate();
		List<String> apiResponse = restTemplate.getForObject("http://localhost:60001/existingStocksInDB", List.class);

		assertTrue(apiResponse.stream().map(Object::toString).allMatch(s -> s.equals("PKN") || s.equals("KGH")));
		assertEquals(apiResponse.size(), 2);
	}

	@Test
	public void cheeckingRestNotExistingStocksInDB() {
		Stock stock1 = new Stock();
		stock1.setTicker("LOXO");
		stock1.setFullName("Loxo Oncology");
		stockRepository.save(stock1);

		RestTemplate restTemplate = new RestTemplate();
		List<String> apiResponse = restTemplate.getForObject("http://localhost:60001/notExistingStockInDB", List.class);

		assertEquals(false, apiResponse.contains("LOXO"));
		assertEquals(true, apiResponse.contains("LUNA"));
	}

}
