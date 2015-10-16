package com.gft.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;

@RestController
public class AlgorithmsGainRest {
	
	@Autowired
	AlgorithmRepository algorithmRepository;
	@Autowired
	StockRepository stockRepository;
	
	@RequestMapping(value="/algorithms/{ticker}")
	public List<String> getGeinOfAlgorithms(@PathVariable("ticker")String ticker){
		
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
		
		ArrayList<String> algorithmList =  (ArrayList<String>)algorithmRepository.findByStock(testStock);
		
		return algorithmList;
	}
}
