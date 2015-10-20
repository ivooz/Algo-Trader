package com.gft.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.aspect.Log;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;
import com.google.gson.Gson;

@RestController
public class AlgorithmsStatesForTickerRest {
@Autowired
AlgorithmRepository algorithmRepo;
	  @Log
	    @RequestMapping(value = "/stock/{ticker}/algorithms")
	    public String getAlgorithmsForSpecificStock(@PathVariable("ticker") String ticker) {
	    	Gson gson = new Gson();
	    	return gson.toJson(algorithmRepo.findByTicker(ticker));
	  }
}
