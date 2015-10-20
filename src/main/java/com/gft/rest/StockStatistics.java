package com.gft.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.aspect.Log;
import com.gft.model.db.Stock;
import com.gft.repository.data.StockRepository;
import com.google.gson.Gson;
@RestController
public class StockStatistics {
	@Autowired
	StockRepository stockRepo;

	@Log
	@RequestMapping(value = "/stock")
	public List<Stock> getStocksHistory() {

		return stockRepo.findAll();
	}
}
