package com.gft.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.model.db.Algorithm;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;
import com.google.gson.Gson;
@RestController
public class StateofAlgorithmForTicker {

	@Autowired
	AlgorithmRepository algorithmRepository;
	@Autowired
	StockRepository stockRepository;

	@RequestMapping(value = "/algorithm/{name}/{ticker}")
	public Algorithm getGeinOfAlgorithms(@PathVariable("name") String name,
			@PathVariable("ticker") String ticker) {


		return algorithmRepository.findByNameandTicker(name, ticker);
	}
}
