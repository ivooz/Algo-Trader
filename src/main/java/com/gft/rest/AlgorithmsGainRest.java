package com.gft.rest;

import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;
import com.google.gson.Gson;

@RestController
public class AlgorithmsGainRest {

	@Autowired
	AlgorithmRepository algorithmRepository;
	@Autowired
	StockRepository stockRepository;

	@RequestMapping(value = "/algorithm/{name}")
	public Algorithm getGeinOfAlgorithms(@PathVariable("name") String name) {

		return algorithmRepository.findByName(name);

	}
}
