package com.gft.rest;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.gft.config.Application;
import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class AlgorithmsGainRestTest {

	@Autowired
	StockRepository stockRepository;
	@Autowired
	AlgorithmRepository algorithmRepository; 
	
	@Test
	public void algorithmsGainRestTest() {
		Stock testStock = new Stock();
		testStock.setFullName("KGHM");
		testStock.setTicker("KGH");
		stockRepository.save(testStock);

		Algorithm algo = new Algorithm();
		algo.setId(1l);
		algo.setStock(testStock);
		algo.setName("MACD");
		algo.setAggregateGain(200.0);
		algo.setAbsoluteGain(400.1);
		algo.setPriceBought(new BigDecimal(100.1));
		algorithmRepository.save(algo);

	//	ArrayList<Algorithm> algorithmList = (ArrayList<Algorithm>) algorithmRepository.findByStock(testStock);
		
		RestTemplate restTemplate = new RestTemplate();
		ArrayList<Object> apiResponse = restTemplate.getForObject("http://localhost:60001/algorithms/KGH",
				ArrayList.class);
		
		System.out.println(apiResponse.toString());
		
	}

}
