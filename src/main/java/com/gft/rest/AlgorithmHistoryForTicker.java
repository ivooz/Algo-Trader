package com.gft.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.model.db.AlgorithmHistory;
import com.gft.repository.data.AlgorithmHistoryRepository;
import com.google.gson.Gson;
@RestController
public class AlgorithmHistoryForTicker {

	@Autowired
	AlgorithmHistoryRepository algorithmHistoryRepository;

	@RequestMapping(value = "/algorithm/{name}/{ticker}/history")
	public List<AlgorithmHistory> getGeinOfAlgorithms(
			@PathVariable("name") String name,
			@PathVariable("ticker") String ticker) {

	
		return algorithmHistoryRepository.findByTickerAndAlgorithmName(name,
				ticker);
	}
}
