package com.gft.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.repository.data.AlgorithmHistoryRepository;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;
import com.google.gson.Gson;
@RestController
public class AlgorithmHistoryForTicker {

	@Autowired
	AlgorithmHistoryRepository algorithmHistoryRepository;

	@RequestMapping(value="/algorithm/{name}/{ticker}/history")
	public String getGeinOfAlgorithms(@PathVariable("name")String name,@PathVariable("ticker")String ticker){
		
		Gson gson = new Gson();
		
		
	return gson.toJson(algorithmHistoryRepository.findByTickerAndAlgorithmName( name,ticker));
}
}
