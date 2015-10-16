package com.gft.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.aspect.LogNoArgs;
import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;

@RestController
public class StockHistoryRest {

	@Autowired
	StockHistoryRepository stockHistoryRepository;
	@Autowired
	StockRepository stockRepository;

	@LogNoArgs
	@RequestMapping(value = "/history/{ticker}")
	public List<StockHistory> getStockHistory(@PathVariable("ticker") String ticker) {

		List<StockHistory> historyOfStock = new ArrayList<StockHistory>(
				stockHistoryRepository.findByStockOrderByDateDesc(stockRepository.findOne(ticker)));

		return historyOfStock;
	}
}
