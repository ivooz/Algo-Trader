package com.gft.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.aspect.Log;
import com.gft.model.db.Algorithm;
import com.gft.repository.data.AlgorithmRepository;
import com.google.gson.Gson;

@RestController
public class AlgorithmsStatesForTickerRest {
@Autowired
AlgorithmRepository algorithmRepo;
	  @Log
	    @RequestMapping(value = "/stock/{ticker}/algorithms")
	    public List<Algorithm> getAlgorithmsForSpecificStock(@PathVariable("ticker") String ticker) {

	    	return algorithmRepo.findByTicker(ticker);
	  }
}
