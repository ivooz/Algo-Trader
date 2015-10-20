package com.gft.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.aspect.Log;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.google.gson.Gson;
@RestController
public class StockStatistics {
	 @Autowired
	    StockRepository stockRepo;

	    @Log
	    @RequestMapping(value = "/stock")
	    public String getStocksHistory() {
	    	Gson gson = new Gson();
	   
	        return gson.toJson(stockRepo.FindStocksWithoutAlgorithms());
	    }
}
